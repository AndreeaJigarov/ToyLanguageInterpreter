package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.Helper.Stack.MyStack;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<IStmt>();
        newStack.push(stmt);
        MyIDictionary<String, IValue> newSymTable = state.getSymTable().clone(); // deep copy                      LOCK
        return new PrgState(newStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap(), state.getLockTable());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + stmt.toString() + ")";
    }
}
