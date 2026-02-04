package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
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
        var symTable = state.getTopSymTable();
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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(varName); // tipul variabilei (trebuie să fie RefType)
        IType typexp = expression.typecheck(typeEnv);  // tipul expresiei
        if (typevar instanceof RefType) {
            RefType reft = (RefType) typevar;
            if (typexp.equals(reft.getInner())) { //if compatible
                return typeEnv;
            } else {
                throw new MyException("WriteHeapStmt: expression type does not match the location type");
            }
        } else {
            throw new MyException("WriteHeapStmt: variable is not of reference type");
        }
    }

    @Override
    public IStmt deepCopy() {
        return this.deepCopy();
    }
}