package Model.Stmt;

import Model.ProgrState.PrgState;


public interface IStmt {
    public PrgState execute(PrgState state) throws Exception;
    //which is the execution method for a statement
    public String toString();
}
