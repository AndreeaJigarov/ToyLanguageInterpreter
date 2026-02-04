package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;


public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
    //which is the execution method for a statement
    public String toString();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
    IStmt deepCopy();
}
