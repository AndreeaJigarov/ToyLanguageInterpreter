package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.List.MyIList;
import Model.ProgrState.PrgState;
import Model.Value.IValue;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" +exp.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        MyIList<IValue> out = state.getOut();
        out.add(val);
        return state;
    }


}
