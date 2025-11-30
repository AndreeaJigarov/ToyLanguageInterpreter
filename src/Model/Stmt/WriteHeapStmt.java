package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.PrgState;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private IExp expression;

    public WriteHeapStmt(String varName, IExp expression) {
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
        if (!(varValue instanceof RefValue refValue))
            throw new MyException("Variable " + varName + " is not a reference");

        int address = refValue.getAddress();
        if (!heap.containsKey(address))
            throw new MyException("Address " + address + " not in heap");

        IValue expValue = expression.eval(symTable, heap);
        if (!expValue.getType().equals(refValue.getLocationType()))
            throw new MyException("Type mismatch in write heap");

        heap.update(address, expValue);
        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varName + "," + expression + ")";
    }
}