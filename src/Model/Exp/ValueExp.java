package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.IType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ValueExp implements IExp{
    IValue e;

    public ValueExp(IValue val) {
        e=val;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) {
        return e;
    }

    @Override
    public String toString(){
        return e.toString();
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return e.getType(); // added for A6
    }

}
