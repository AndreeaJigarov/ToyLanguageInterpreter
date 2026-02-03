package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Lock.ILockTable;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import java.util.concurrent.locks.ReentrantLock;

public class LockStmt implements IStmt {
    private final String var;

    public LockStmt(String var) { this.var = var; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ILockTable lockTable = state.getLockTable();

        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.containsKey(var)) { // make sure variable in symtable
            throw new MyException("Lock Error: Variable " + var + " not found in Symbol Table.");
        }

        int foundIndex = ((IntValue) state.getSymTable().lookup(var)).getValue();
        ReentrantLock lock = lockTable.getLock(); //get the Java lock from our lockTable to make the lock mechanism

        lock.lock();
        try {
            if (!lockTable.contains(foundIndex)) throw new MyException("Lock index not found"); // if found idx not in SymTable => Error
            if (lockTable.get(foundIndex) == -1) {
                lockTable.update(foundIndex, state.getId()); // if lock free , get it
            } else {
                state.getExeStack().push(this); // if not , back to stack, it will try again
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public Model.ProgrState.Helper.Dictionary.MyIDictionary<String, IType> typecheck(Model.ProgrState.Helper.Dictionary.MyIDictionary<String, IType> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) return typeEnv;
        throw new MyException("Lock variable must be int");
    }

    @Override
    public String toString() { return "lock(" + var + ")"; }
}