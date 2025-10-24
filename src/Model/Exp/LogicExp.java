package Model.Exp;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;
import com.sun.jdi.Value;

public class LogicExp implements IExp{
    private IExp e1;
    private IExp e2;
    int op; //1-and , 2-or, 3-not


    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) {
        Value v1 = e1.eval(tbl);
    }

}
