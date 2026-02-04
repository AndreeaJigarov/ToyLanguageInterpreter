package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStmt implements IStmt {
    private IExp cond;
    private IStmt body;

    public WhileStmt(IExp cond, IStmt body) { this.cond = cond; this.body = body; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = cond.eval(state.getTopSymTable(), state.getHeap());
        if (!(val instanceof BoolValue bv)) throw new MyException("While condition not boolean");

        if (bv.getValue()) {
            state.getExeStack().push(this);  // repeat while
            state.getExeStack().push(body);
        }
        return null;
    }

    @Override public String toString() { return "while(" + cond + ") " + body; }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = cond.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            body.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("While condition not boolean");
        }
    }

    @Override
    public IStmt deepCopy() {
        return this.deepCopy();
    }
}