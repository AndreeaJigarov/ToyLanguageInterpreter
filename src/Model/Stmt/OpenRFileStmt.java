package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.PrgState;
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
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
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
        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }

}
