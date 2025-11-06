package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Exp.IExp;
import Model.Type.IType;
import Model.Value.IValue;


public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    } // maybe not?




    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if (symTbl.containsKey(id)){
            IValue value = exp.eval(symTbl);
            IType typId = (symTbl.get(id)).getType();
            if(value.getType().equals(typId)){
                symTbl.update(id,value);
            }
            else{
                throw new MyException("declared type of variable" +id+" and type of assigned expression do not match");

            }
        }else{
            throw new MyException("the used variable "+id+" nwas not declared before");

        }
        return state;
    }

    @Override
    public String toString() {
        return id+"="+exp.toString();
    }

}
