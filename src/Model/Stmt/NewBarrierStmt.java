package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class NewBarrierStmt implements IStmt {
    private final String var;
    private final IExp exp;

    public NewBarrierStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Evaluate expression using current SymTable and Heap
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!val.getType().equals(new IntType())) {
            throw new MyException("NewBarrier Error: Expression result is not an integer.");
        }

        int number = ((IntValue) val).getValue();

        // Add entry to the shared BarrierTable
        // The table's add method is synchronized for thread safety
        int newLocation = state.getBarrierTable().add(new Pair<>(number, new ArrayList<>()));

        // Update the SymTable for the current thread
        if (state.getSymTable().containsKey(var)) {
            state.getSymTable().update(var, new IntValue(newLocation));
        } else {
            state.getSymTable().put(var, new IntValue(newLocation));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!typeEnv.lookup(var).equals(new IntType()))
            throw new MyException("NewBarrier Typecheck: Variable " + var + " is not an integer.");
        if (!exp.typecheck(typeEnv).equals(new IntType()))
            throw new MyException("NewBarrier Typecheck: Expression is not an integer.");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newBarrier(" + var + ", " + exp.toString() + ")";
    }
}