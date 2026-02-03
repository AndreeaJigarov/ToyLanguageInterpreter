package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Latch.ILatchTable;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private final String var;
    private final IExp exp;

    public NewLatchStmt(String var, IExp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Get references to the global LatchTable and its lock
        ILatchTable latchTable = state.getLatchTable();
        ReentrantLock lock = latchTable.getLock();

        lock.lock();
        try {
            // Evaluate the expression to get the initial count
            IValue val = exp.eval(state.getSymTable(), state.getHeap());

            // Check if the evaluated expression result is an integer
            if (!val.getType().equals(new IntType())) {
                throw new MyException("NewLatch: The expression " + exp + " did not evaluate to an integer.");
            }

            int number = ((IntValue) val).getValue();

            // Create a new entry in the LatchTable and get the location (index)
            int newAddr = latchTable.allocate(number);

            // Update the Symbol Table: map 'var' to the new latch location
            // If 'var' exists, update it; if not, add it
            state.getSymTable().put(var, new IntValue(newAddr));

        } finally {
            // Ensure the lock is released regardless of success or failure
            lock.unlock();
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // Verify that the variable exists and is of type int [cite: 210]
        IType typeVar = typeEnv.lookup(var);

        // Verify that the expression evaluates to an int
        IType typeExp = exp.typecheck(typeEnv);

        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("NewLatch: Both the variable and the expression must have IntType.");
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + var + ", " + exp + ")";
    }
}