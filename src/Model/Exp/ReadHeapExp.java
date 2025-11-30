package Model.Exp;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Value.IValue;
import Model.Value.RefValue;

public class ReadHeapExp implements IExp {
    private IExp exp;

    public ReadHeapExp(IExp exp) { this.exp = exp; }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue val = exp.eval(symTable, heap);
        if (!(val instanceof RefValue rv)) throw new MyException("rH: not a reference");
        int addr = rv.getAddress();
        return heap.get(addr);
    }

    @Override public String toString() { return "rH(" + exp + ")"; }
}