package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Barrier.IBarrierTable;
import Model.ProgrState.Helper.Barrier.BarrierEntry;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStmt implements IStmt {
    private final String var;
    private final IExp exp;

    public NewBarrierStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IBarrierTable barrierTable = state.getBarrierTable();
        ReentrantLock lock = barrierTable.getLock();

        lock.lock();
        try {
            // Evaluate expression to get the threshold (number)
            IValue val = exp.eval(state.getSymTable(), state.getHeap());
            if (!val.getType().equals(new IntType()))
                throw new MyException("NewBarrier: Expression result is not an integer");

            int number = ((IntValue) val).getValue();

            // Create new entry (number, empty list) and add to table
            int newAddr = barrierTable.allocate(new BarrierEntry(number));

            // Update or add variable in SymTable with the new address
            state.getSymTable().put(var, new IntValue(newAddr));

        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var);
        IType typeExp = exp.typecheck(typeEnv);

        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("NewBarrier: var and exp must be of type int");
    }

    @Override
    public String toString() {
        return "newBarrier(" + var + ", " + exp + ")";
    }
}