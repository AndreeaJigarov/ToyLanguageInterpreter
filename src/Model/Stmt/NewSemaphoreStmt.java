package Model.Stmt;


import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Semaphore.ISemaphoreTable;
import Model.ProgrState.Helper.Semaphore.SemaphoreTriple;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Value.IntValue;

import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStmt implements IStmt {
    private final String var;
    private final IExp exp1, exp2;

    public NewSemaphoreStmt(String var, IExp exp1, IExp exp2) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        ISemaphoreTable semTable = state.getSemaphoreTable();
        ReentrantLock lock = semTable.getLock();
        lock.lock();
        try {
            int n1 = ((IntValue) exp1.eval(state.getSymTable(), state.getHeap())).getValue();
            int n2 = ((IntValue) exp2.eval(state.getSymTable(), state.getHeap())).getValue();
            int newAddr = semTable.allocate(new SemaphoreTriple(n1, n2));

            if (state.getSymTable().containsKey(var)) {
                state.getSymTable().update(var, new IntValue(newAddr));
            } else {
                throw new MyException("variable not in sym table");
            }
        } finally { lock.unlock(); }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString() {
        return "newSemaphore(" + var + "," + exp1 + "," + exp2+ ")";
    }
}