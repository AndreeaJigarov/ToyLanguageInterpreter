package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Latch.ILatchTable;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private final String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ILatchTable latchTable = state.getLatchTable();
        ReentrantLock lock = latchTable.getLock();

        lock.lock();
        try {
            // 1. Find the variable in SymTable
            if (!state.getSymTable().containsKey(var))
                throw new MyException("Await: Variable " + var + " not found in SymTable.");

            int foundIndex = ((IntValue) state.getSymTable().lookup(var)).getValue();

            // 2. Verify the index exists in LatchTable
            if (!latchTable.contains(foundIndex))
                throw new MyException("Await: Index " + foundIndex + " not found in LatchTable.");

            // 3. Execution Logic
            if (latchTable.get(foundIndex) == 0) {
                // If the count is 0, do nothing and let the thread proceed
            } else {
                // Otherwise, push the statement back onto the stack to wait
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // Verify that var is an integer
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("Await: Variable " + var + " must be of IntType.");
        }
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}