package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.PrgState;
import Model.Exp.IExp;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class NewStmt implements IStmt {
    private String varName;
    private IExp expression;

    public NewStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.containsKey(varName))
            throw new MyException("Variable " + varName + " not declared");

        IValue varValue = symTable.get(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new MyException("Variable " + varName + " is not of reference type");

        IValue expValue = expression.eval(symTable, heap);
        RefType refType = (RefType) varValue.getType();

        if (!expValue.getType().equals(refType.getInner()))
            throw new MyException("Expression type doesn't match location type");

        int newAddress = heap.allocate(expValue);
        symTable.update(varName, new RefValue(newAddress, refType.getInner()));

        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + "," + expression + ")";
    }
}