package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class MulExp implements IExp {
    private final IExp exp1;
    private final IExp exp2;

    public MulExp(IExp exp1, IExp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException {
        // Evaluate both expressions to get their values
        IValue v1 = exp1.eval(tbl, heap);
        IValue v2 = exp2.eval(tbl, heap);

        // Ensure both are integers before calculating
        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            int n1 = ((IntValue) v1).getValue();
            int n2 = ((IntValue) v2).getValue();

            // Formula: ((exp1 * exp2) - (exp1 + exp2))
            return new IntValue((n1 * n2) - (n1 + n2));
        } else {
            throw new MyException("MUL operands must be integers.");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t1 = exp1.typecheck(typeEnv);
        IType t2 = exp2.typecheck(typeEnv);

        if (t1.equals(new IntType()) && t2.equals(new IntType())) {
            return new IntType();
        } else {
            throw new MyException("MUL expressions must evaluate to integers.");
        }
    }

    @Override
    public String toString() {
        return "MUL(" + exp1 + ", " + exp2 + ")";
    }
}