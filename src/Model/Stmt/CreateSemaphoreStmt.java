package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Semaphore.ISemaphoreTable;
import Model.ProgrState.Helper.Semaphore.SemaphoreEntry;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStmt implements IStmt {
    private final String var;
    private final IExp exp;

    public CreateSemaphoreStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ISemaphoreTable semTable = state.getSemaphoreTable();
        ReentrantLock lock = semTable.getLock();

        lock.lock();
        try {
            // Evaluate expression to get the capacity (N1)
            IValue val = exp.eval(state.getSymTable(), state.getHeap());
            if (!val.getType().equals(new IntType()))
                throw new MyException("CreateSemaphore: Expression result is not an integer");

            int number1 = ((IntValue) val).getValue();

            // Create new entry (number1, empty list) and add to table
            int newAddr = semTable.allocate(new SemaphoreEntry(number1));

            // Update SymTable with the new address
            if (state.getSymTable().containsKey(var) && state.getSymTable().lookup(var).getType().equals(new IntType())) {
                state.getSymTable().update(var, new IntValue(newAddr));
            } else {
                throw new MyException("CreateSemaphore: Variable " + var + " not defined as int in SymTable");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var);
        IType typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType())) return typeEnv;
        else throw new MyException("CreateSemaphore: var and exp must be of type int ");
    }

    @Override
    public String toString() {
        return "createSemaphore(" + var + ", " + exp + ")";
    }
}