package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStmt implements IStmt{
    IExp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp exp, IStmt thenS,  IStmt elseS){
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString(){
        return "(IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+"))";
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl =  state.getSymTable();
        MyIStack<IStmt> stk = state.getExeStack();

        IValue cond = exp.eval(symTbl);
        if (!cond.getType().equals(new BoolType())) {
            throw new MyException("Condition Type Error");
        }
        BoolValue bv = (BoolValue) cond;
        if(bv.getValue())stk.push(thenS);
        else  stk.push(elseS);
        return state;
    }



}
