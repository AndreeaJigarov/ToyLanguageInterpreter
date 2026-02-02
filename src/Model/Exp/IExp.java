package Model.Exp;
import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.IType;
import Model.Value.IValue;


public interface IExp {
    IValue eval(MyIDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws MyException;
    public String toString();
    IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
