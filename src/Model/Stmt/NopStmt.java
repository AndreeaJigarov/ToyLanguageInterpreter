package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.PrgState;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }
    @Override
    public String toString(){
        return "";
    }
}
