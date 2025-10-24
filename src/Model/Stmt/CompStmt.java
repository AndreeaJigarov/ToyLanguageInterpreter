package Model.Stmt;

import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;


    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public PrgState execute(PrgState state){
        MyIStack<IStmt> stack = state.getStk();
        stack.push(second);
        stack.push(first);
        return state;
    }

    public String toString(){
        return "("+first.toString() + ";" + second.toString()+")";
    }




}
