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
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt {
    private final String var;

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ISemaphoreTable semTable = state.getSemaphoreTable();
        ReentrantLock lock = semTable.getLock();

        lock.lock();
        try {
            if (!state.getSymTable().containsKey(var)) throw new MyException("Acquire: Variable not found");
            IValue val = state.getSymTable().lookup(var);
            if (!val.getType().equals(new IntType())) throw new MyException("Acquire: Variable is not int");

            int foundIndex = ((IntValue) val).getValue();
            if (!semTable.contains(foundIndex)) throw new MyException("Acquire: Index not in Semaphore Table");

            SemaphoreEntry entry = semTable.get(foundIndex);
            int N1 = entry.getCapacity();
            List<Integer> list1 = entry.getThreadIds();
            int NL = list1.size();

            if (N1 > NL) {
                if (!list1.contains(state.getId())) {
                    list1.add(state.getId());
                    semTable.update(foundIndex, entry);
                }
            } else {
                state.getExeStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        else throw new MyException("Acquire: Variable " + var + " must be int");
    }

    @Override
    public String toString() {
        return "acquire(" + var + ")";
    }
}