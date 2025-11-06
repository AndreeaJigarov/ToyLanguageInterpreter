package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String v, IType t) {
        name = v;
        type = t;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTbl = state.getSymTable();
        if(symTbl.containsKey(name))throw new MyException("Variable "+name+" already exists");
        symTbl.put(name, type.defaultValue());
        return state;
    }
    @Override
    public String toString(){
        return this.type+" "+name;
    }
}
