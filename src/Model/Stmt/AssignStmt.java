package Model.Stmt;

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
    public String toString() {
        return id+"="+exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws Exception {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if (symTbl.containsKey(id)){
            IValue value = exp.eval(symTbl);
            IType typId = (symTbl.get(id)).getType();
            if(value.getType().equals(typId)){
                symTbl.update(id,value);
            }
            else{
                throw new Exception("declared type of variable" +id+" and type of assigned expression do not match");

            }
        }else{
            throw new Exception("the used variable "+id+" nwas not declared before");

        }
        return state;
    }


}
