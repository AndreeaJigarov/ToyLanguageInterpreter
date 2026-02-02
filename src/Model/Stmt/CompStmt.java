package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Type.IType;
import Model.Type.StringType;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;


    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public PrgState execute(PrgState state){
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        //return state;
        return null;
    }

    public String toString(){
        return first + ";\n" + second;
        //return "("+first + ";" + second +")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        //return second.typecheck(first.typecheck(typeEnv)); -- or directly like this
        MyIDictionary<String, IType> typEnv1 = first.typecheck(typeEnv);
        MyIDictionary<String, IType> typEnv2 = second.typecheck(typEnv1);
        return typEnv2; // this makes all logic of typecheck prgstate function !!!!
    }


}
