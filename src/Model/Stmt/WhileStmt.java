package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.PrgState;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStmt implements IStmt {
    private IExp cond;
    private IStmt body;

    public WhileStmt(IExp cond, IStmt body) { this.cond = cond; this.body = body; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = cond.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof BoolValue bv)) throw new MyException("While condition not boolean");

        if (bv.getValue()) {
            state.getExeStack().push(this);  // repeat while
            state.getExeStack().push(body);
        }
        return null;
    }

    @Override public String toString() { return "while(" + cond + ") " + body; }
}