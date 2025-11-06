package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.PrgState;


public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement
    public String toString();
}
