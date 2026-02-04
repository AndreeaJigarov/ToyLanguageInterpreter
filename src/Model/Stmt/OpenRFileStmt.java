package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    private IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getTopSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType())) {
            throw new MyException("Expression not StringType");
        }
        StringValue fileName = (StringValue) val;
        if (state.getFileTable().containsKey(fileName)) {
            throw new MyException("File already open");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName.getValue()));
            state.getFileTable().put(fileName, br);
        } catch (IOException e) {
            throw new MyException("File open error: " + e.getMessage());
        }
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) return typeEnv;
        else throw new MyException("Name of file not StringType");
    }

    @Override
    public IStmt deepCopy() {
        return this.deepCopy();
    }

}
