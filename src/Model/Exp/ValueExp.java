package Model.Exp;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;

public class ValueExp implements IExp{
    IValue e;

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) {
        return e;
    }

}
