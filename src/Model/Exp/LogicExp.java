package Model.Exp;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicExp implements IExp{
    private IExp e1;
    private IExp e2;
    int op; //1-and , 2-or, 3-not

    public LogicExp(IExp e1, IExp e2, String Sop) {
        this.e1 = e1;
        this.e2 = e2;
        switch(Sop){
            case "and":
                op =1;
                break;
            case"or":
                op =2;
                break;
            case "not":
                op =3;
        break;}

    }


    public String getStringOperator(){
        return switch(op){
            case 1-> "&&";
            case 2-> "||";
            case 3-> "!";
            default->"";
        };
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws Exception {
        IValue v1 = e1.eval(tbl);
        IValue v2 = e2.eval(tbl);

        if (op == 3) {
            if (v1.getType().equals(new BoolType())) {
                BoolValue boolval = (BoolValue) v1;
                return new BoolValue(!boolval.getValue());
            }
        } else {
            if (v2.getType().equals(new BoolType()) && v1.getType().equals(new BoolType())) {
                BoolValue boolval2 = (BoolValue) v2;
                BoolValue boolval1 = (BoolValue) v1;
                if (op == 1) {
                    return new BoolValue(boolval2.getValue() && boolval1.getValue());
                } else if (op == 2) {
                    return new BoolValue(boolval2.getValue() || boolval1.getValue());
                }
            }
            throw new Exception("Operands must be bool");
        }
        return null;  // WHY DOES IT REQUIRE ME TO RETURN STH :(
    }


}
