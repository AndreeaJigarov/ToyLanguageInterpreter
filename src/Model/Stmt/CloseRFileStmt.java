package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType())) {
            throw new MyException("Exp not string");
        }
        StringValue fileName = (StringValue) val;
        if (!state.getFileTable().containsKey(fileName)) {
            throw new MyException("File not open");
        }
        BufferedReader br = state.getFileTable().lookup(fileName);
        try {
            br.close();
        } catch (IOException e) {
            throw new MyException("Close error: " + e.getMessage());
        }
        state.getFileTable().remove(fileName);
        //return state;
        return null;
    }

    @Override
    public String toString() {
        return "closeRFile: " + exp.toString() ;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("CloseRFileStmt: expression is not of StringType");
        }
    }
}