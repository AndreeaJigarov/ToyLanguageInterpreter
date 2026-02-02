package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
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
        //return state;
        return null;

    }

    @Override
    public String toString() {
        return "readFile: " + exp.toString() + " , " + varName ;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);  // exp = numele fișierului
        if (!typexp.equals(new StringType())) {
            throw new MyException("ReadFileStmt: expression is not of StringType");
        }
        IType typevar = typeEnv.lookup(varName);  // varName unde se salveaza valoarea citită
        if (!typevar.equals(new IntType())) {
            throw new MyException("ReadFileStmt: variable is not of IntType");
        }
        return typeEnv;
    }
}
