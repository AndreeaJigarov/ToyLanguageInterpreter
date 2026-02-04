package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Barrier.IBarrierTable;
import Model.ProgrState.Helper.Barrier.BarrierEntry;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private final String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IBarrierTable barrierTable = state.getBarrierTable();
        ReentrantLock lock = barrierTable.getLock();

        lock.lock();
        try {
            //Check if variable exists and is an integer
            if (!state.getSymTable().containsKey(var))
                throw new MyException("Await: Variable " + var + " not found");

            IValue val = state.getSymTable().lookup(var);
            if (!val.getType().equals(new IntType()))
                throw new MyException("Await: Variable " + var + " is not an integer");

            int foundIndex = ((IntValue) val).getValue();

            //Verify the index exists in the Barrier Table
            if (!barrierTable.contains(foundIndex))
                throw new MyException("Await: Barrier index not found in table");

            BarrierEntry entry = barrierTable.get(foundIndex);
            int N1 = entry.getThreshold();
            List<Integer> list1 = entry.getThreadIds();
            int NL = list1.size();

            //Blocking Logic
            if (N1 > NL) {
                // If the current thread isn't already waiting, add it
                if (!list1.contains(state.getId())) {
                    list1.add(state.getId());
                }
                // Push back onto the stack to wait for the next execution cycle if in list and if not in list
                state.getExeStack().push(this);
            } else {
                // Dacă NL == N, nu facem nimic (pop-ul este deja făcut în ProgramState.oneStep),
                // deci thread-ul trece de barieră.
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("Await: Variable " + var + " must be int");
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}