package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IntValue;

public class NewLockStmt implements IStmt {
    private final String var;

    public NewLockStmt(String var) { this.var = var; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        int addr = state.getLockTable().allocate(-1); // at first no thread has the lock
        if (state.getSymTable().containsKey(var)) state.getSymTable().update(var, new IntValue(addr)); //update if it exists
        else state.getSymTable().put(var, new IntValue(addr));  // add if not
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) return typeEnv; // make sure it is an int
        throw new MyException("NewLock: Variable is not int");
    }

    @Override
    public String toString() { return "newLock(" + var + ")"; }
}