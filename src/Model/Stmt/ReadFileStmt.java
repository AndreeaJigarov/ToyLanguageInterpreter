package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.PrgState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private IExp exp;
    private String varName;

    public ReadFileStmt(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().containsKey(varName) || !state.getSymTable().lookup(varName).getType().equals(new IntType())) {
            throw new MyException("Var not defined or not int");
        }

        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new StringType())) {
            throw new MyException("Exp not string");
        }

        StringValue fileName = (StringValue) val;
        BufferedReader br = state.getFileTable().lookup(fileName);

        try {
            String line = br.readLine();
            IntValue intVal = (line == null) ? new IntValue(0) : new IntValue(Integer.parseInt(line));
            state.getSymTable().update(varName, intVal);
        } catch (IOException | NumberFormatException e) {
            throw new MyException("Read error: " + e.getMessage());
        }
        return state;

    }

    @Override
    public String toString() {
        return "readFile: " + exp.toString() + " , " + varName ;
    }
}
