package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Latch.ILatchTable;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownStmt implements IStmt {
    private final String var;

    public CountDownStmt(String var) {
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
                throw new MyException("CountDown: Variable " + var + " not found in SymTable.");

            int foundIndex = ((IntValue) state.getSymTable().lookup(var)).getValue();

            // 2. Execution Logic
            if (latchTable.contains(foundIndex)) {
                int currentCount = latchTable.get(foundIndex);
                if (currentCount > 0) {
                    // Decrement the value in LatchTable
                    latchTable.update(foundIndex, currentCount - 1);
                    // write the current thread ID to the Out table
                    state.getOut().add(new IntValue(state.getId()));
                }

            }
            // If the index is not found or count is already 0, do nothing
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
            throw new MyException("CountDown: Variable " + var + " must be of IntType.");
        }
    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }
}