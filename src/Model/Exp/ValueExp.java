package Model.Exp;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ValueExp implements IExp{
    IValue e;

    public ValueExp(IValue val) {
        e=val;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) {
        return e;
    }

    @Override
    public String toString(){
        return e.toString();
    }

}
