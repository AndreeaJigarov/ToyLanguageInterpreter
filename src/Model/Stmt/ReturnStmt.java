package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.PrgState;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Type.IType;

public class ReturnStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        try {
            state.getSymTableStack().pop(); //pop top SymTable
        } catch (Exception e) {
            throw new MyException("Return: symtblStack empty" + e.getMessage());
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public String toString() { return "return"; }
}