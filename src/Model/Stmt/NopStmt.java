package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException {
       // return state;
        return null;
    }
    @Override
    public String toString(){
        return "";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
