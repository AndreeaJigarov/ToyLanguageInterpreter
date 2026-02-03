package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Lock.ILockTable;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStmt implements IStmt {
    private final String var;

    public UnlockStmt(String var) { this.var = var; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        ILockTable lockTable = state.getLockTable();

        if (!symTable.containsKey(var)) {
            throw new MyException("Lock Error: Variable " + var + " not found in Symbol Table.");
        }

        int foundIndex = ((IntValue) state.getSymTable().lookup(var)).getValue();
        ReentrantLock lock = lockTable.getLock();

        lock.lock();
        try {
            if (!lockTable.contains(foundIndex)) {
                return null; // if not in Locktable do nothing
            }
            if (lockTable.get(foundIndex) == state.getId()) {
                lockTable.update(foundIndex, -1);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public Model.ProgrState.Helper.Dictionary.MyIDictionary<String, IType> typecheck(Model.ProgrState.Helper.Dictionary.MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        throw new MyException("Unlock variable must be int");
    }

    @Override
    public String toString() { return "unlock(" + var + ")"; }
}