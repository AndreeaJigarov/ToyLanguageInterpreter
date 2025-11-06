package Model.Exp;
import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.Value.IValue;


public interface IExp {
    IValue eval(MyIDictionary<String, IValue> tbl ) throws MyException;
    public String toString();
}
