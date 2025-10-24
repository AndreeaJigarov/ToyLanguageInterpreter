package Model.Stmt;

import Model.Exp.IExp;
import Model.ProgrState.PrgState;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print(" +exp.toString() + ")";
    }

    public PrgState execute(PrgState state){
        //System.out.print(exp.toString()); ??
        // outlist.append(this.toString) ; exeStack.pop();
        return state;
    }


}
