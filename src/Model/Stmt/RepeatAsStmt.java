package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;

import java.awt.image.ComponentColorModel;


public class RepeatAsStmt implements IStmt {
    private final IStmt statement;
    private final IExp expression;



    public RepeatAsStmt(IStmt statement, IExp expression) {
        this.statement = statement;
        this.expression = expression;
    }


    //stmt1;(while(exp2) stmt1)
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt whileStmt = new WhileStmt(expression, statement);
        state.getExeStack().push(new CompStmt(statement , whileStmt));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("Repeat As Error: Condition must be boolean.");
        }

    }

    @Override
    public String toString() {
       return "(repeat(" + statement.toString() + ") as " + expression.toString() +")";
    }
}
