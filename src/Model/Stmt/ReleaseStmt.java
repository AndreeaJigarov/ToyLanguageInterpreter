package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Semaphore.ISemaphoreTable;
import Model.ProgrState.Helper.Semaphore.SemaphoreEntry;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements IStmt {
    private final String var;

    public ReleaseStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ISemaphoreTable semTable = state.getSemaphoreTable();
        ReentrantLock lock = semTable.getLock();

        lock.lock();
        try {
            if (!state.getSymTable().containsKey(var)) throw new MyException("Release: Variable not found");
            int foundIndex = ((IntValue) state.getSymTable().lookup(var)).getValue();

            if (!semTable.contains(foundIndex)) throw new MyException("Release: Index not in Semaphore Table");

            SemaphoreEntry entry = semTable.get(foundIndex);
            // Remove current thread ID from list if present
            entry.getThreadIds().remove(Integer.valueOf(state.getId()));
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        else throw new MyException("Release: Variable " + var + " must be int");
    }

    @Override
    public String toString() {
        return "release(" + var + ")";
    }
}