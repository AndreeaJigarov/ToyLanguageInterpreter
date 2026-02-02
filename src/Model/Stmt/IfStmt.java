package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if(typexp.equals(new  BoolType())){
            thenS.typecheck(typeEnv.clone());
            elseS.typecheck(typeEnv.clone());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");

    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl =  state.getSymTable();
        MyIStack<IStmt> stk = state.getExeStack();
        IHeap<Integer, IValue> heap = state.getHeap();

        IValue cond = exp.eval(symTbl, heap);
        if (!cond.getType().equals(new BoolType())) {
            throw new MyException("Condition Type Error");
        }
        BoolValue bv = (BoolValue) cond;
        if(bv.getValue())stk.push(thenS);
        else  stk.push(elseS);
        //return state;
        return null;

    }



}
