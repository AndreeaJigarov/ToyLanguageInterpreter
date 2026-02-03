package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Semaphore.ISemaphoreTable;
import Model.ProgrState.Helper.Semaphore.SemaphoreTriple;
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
            // 1. FoundIndex = lookup(SymTable, var)
            if (!state.getSymTable().containsKey(var)) {
                throw new MyException("Release error: variable " + var + " is not in SymTable.");
            }

            IValue val = state.getSymTable().lookup(var);

            // 2. If var has not int type then print an error message and terminate
            if (!val.getType().equals(new IntType())) {
                throw new MyException("Release error: variable " + var + " is not of type int.");
            }

            int foundIndex = ((IntValue) val).getValue();

            // 3. If foundIndex is not an index in the Semaphore Table then print error
            if (!semTable.contains(foundIndex)) {
                throw new MyException("Release error: index " + foundIndex + " is not in the Semaphore Table.");
            }

            // 4. Retrieve the entry (N1, List1, N2)
            SemaphoreTriple entry = semTable.get(foundIndex);

            // 5. If (the identifier of the current PrgState is in List1)
            if (entry.getThreadIds().contains(state.getId())) {
                // Remove the identifier of the current PrgState from List1
                entry.getThreadIds().remove(Integer.valueOf(state.getId()));
                semTable.update(foundIndex, entry); // Sync back to table
            }
            // else do nothing

        } finally {
            // Lookup and update must be an atomic operation
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // The typechecker must not be adapted and called for this problem
        return typeEnv;
    }

    @Override
    public String toString() {
        return "release(" + var + ")";
    }
}