package Model.Exp;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

public class RelationalExp implements IExp {
    private IExp exp1, exp2;
    private String op;  // "<", "<=", "==", "!=", ">", ">="

    public RelationalExp(IExp exp1, IExp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException {
        IValue v1 = exp1.eval(tbl, heap);
        IValue v2 = exp2.eval(tbl, heap);
        if (!v1.getType().equals(new IntType()) || !v2.getType().equals(new IntType())) {
            throw new MyException("Operands not int");
        }
        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();
        return switch (op) {
            case "<" -> new BoolValue(n1 < n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case ">" -> new BoolValue(n1 > n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new MyException("Invalid operator");
        };
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + op + " " + exp2.toString();
    }
}