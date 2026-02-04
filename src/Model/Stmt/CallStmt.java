package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyDictionary;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallStmt implements IStmt {
    private String fname;
    private List<IExp> actualParams;

    public CallStmt(String fname, List<IExp> params) {
        this.fname = fname;
        this.actualParams = params;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getProcTable().isDefined(fname))
            throw new MyException("Procedure " + fname + " not defined!");

        List<String> formalParams = state.getProcTable().getParams(fname);
        IStmt body = state.getProcTable().getBody(fname);

        // Evaluate actual parameters using CURRENT SymTable
        MyIDictionary<String, IValue> newSymTable = state.getTopSymTable().clone(); // TO SEE GLOBAL VARIABKESSSSSS
        for (int i = 0; i < formalParams.size(); i++) {
            try {
                IValue val = actualParams.get(i).eval(state.getTopSymTable(), state.getHeap());
                newSymTable.put(formalParams.get(i), val);
            } catch (Exception e) {
                throw new MyException("CallStmt: " + e.getMessage());
            }
        }

        // Context switching
        state.getSymTableStack().push(newSymTable);
        state.getExeStack().push(new ReturnStmt());
        state.getExeStack().push(body);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    public IStmt deepCopy() {
        return new CallStmt(fname, new ArrayList<>(actualParams));
    }

    public String toString() {
        return "(call" + " " + fname + " "+ actualParams.toString() + ")";
    }
}