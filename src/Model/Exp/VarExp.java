package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;

public class VarExp implements IExp{
    String id;

    public VarExp(String id){
        this.id = id;
    }


    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws MyException {
        if(tbl.containsKey(id)){
            return tbl.get(id);
        }
        else throw new MyException("Variable "+id+" not found");
    }

    @Override
    public String toString(){
        return id;
    }

}
