package Model.Exp;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;

public class VarExp implements IExp{
    String id;

    VarExp(String id){
        this.id = id;
    }


    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws Exception {
        if(tbl.containsKey(id)){
            return tbl.get(id);
        }
        else throw new Exception("Variable "+id+" not found");
    }

}
