package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import javafx.util.Pair;
import java.util.List;

public class AwaitStmt implements IStmt {
    private final String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Look up variable in SymTable
        if (!state.getSymTable().containsKey(var)) {
            throw new MyException("Await Error: Variable " + var + " not found in SymTable.");
        }

        IValue val = state.getSymTable().lookup(var);
        int foundIndex = ((IntValue) val).getValue();

        // Perform atomic check-and-update on the shared BarrierTable
        synchronized (state.getBarrierTable()) {
            if (!state.getBarrierTable().contains(foundIndex)) {
                throw new MyException("Await Error: Index " + foundIndex + " not in Barrier Table.");
            }

            Pair<Integer, List<Integer>> entry = state.getBarrierTable().get(foundIndex);
            int N1 = entry.getKey(); // Capacity
            List<Integer> L1 = entry.getValue(); // List of threads
            int NL = L1.size();

            if (N1 > NL) {
                if (!L1.contains(state.getId())) {
                    L1.add(state.getId());
                }
                // Requirement: push back await(var) on the ExeStack
                state.getExeStack().push(this);
            }
        }
        // If N1 == NL, the statement is simply popped, and execution continues.
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("Await Typecheck: Variable " + var + " must be an integer.");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}