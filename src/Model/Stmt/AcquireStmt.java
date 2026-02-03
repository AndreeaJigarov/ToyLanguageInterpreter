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

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt {
    private final String var;


    public AcquireStmt(String var) { this.var = var; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ISemaphoreTable semTable = state.getSemaphoreTable();
        ReentrantLock lock = semTable.getLock();

        lock.lock();
        try {
            // 1. Check if var exists in SymTable
            if (!state.getSymTable().containsKey(var)) {
                throw new MyException("Acquire error: variable " + var + " is not in SymTable.");
            }

            // 2. Check if var has the type int
            IValue val = state.getSymTable().lookup(var);
            if (!val.getType().equals(new IntType())) {
                throw new MyException("Acquire error: variable " + var + " is not of type int.");
            }

            int foundIndex = ((IntValue) val).getValue();

            // 3. Check if foundIndex is an index in the Semaphore Table
            if (!semTable.contains(foundIndex)) {
                throw new MyException("Acquire error: index " + foundIndex + " is not in the Semaphore Table.");
            }

            // 4. Retrieve the entry (N1, List1, N2)
            SemaphoreTriple entry = semTable.get(foundIndex);
            int N1 = entry.getN1();
            int N2 = entry.getN2();
            List<Integer> list1 = entry.getThreadIds();
            int NL = list1.size(); //

            // 5. Condition: (N1 - N2) > NL
            if ((N1 - N2) > NL) {
                // if the identifier of the current PrgState is in List1, do nothing
                if (!list1.contains(state.getId())) {
                    list1.add(state.getId());
                    semTable.update(foundIndex, entry);
                }
            } else {
                // push back acquire(var) on the ExeStack
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }
}