package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class NotExp implements IExp {

    private final IExp exp;

    public NotExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue val = exp.eval(symTable, heap);
        if (!(val instanceof BoolValue bv)) {
            throw new MyException("Not a bool value!!!");
        }
        return new BoolValue(!bv.getValue());

    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type = exp.typecheck(typeEnv);
        if (type.equals(new BoolType())) {
            return new BoolType();
        }else {
            throw new MyException("Not a bool value!!!");
        }
    }
    @Override
    public String toString() {
        return "!(" + exp.toString() + ")";
    }
}
