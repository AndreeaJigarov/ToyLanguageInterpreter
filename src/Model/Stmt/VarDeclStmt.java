package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
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
        var symTbl = state.getTopSymTable();
        if(symTbl.containsKey(name))throw new MyException("Variable "+name+" already exists");
        symTbl.put(name, type.defaultValue());
        //return state;
        return null;
    }
    @Override
    public String toString(){
        return this.type+" "+name ;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return this.deepCopy();
    }
}
