package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.Exp.NotExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;

// ALSO USES NOTEXPR  , custom made in Expressions
public class RepeatUntilStmt implements IStmt {
    private final IStmt statement;
    private final IExp expression;

    public RepeatUntilStmt(IStmt statement, IExp expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Requirement 2a: create stmt1; (while(!exp2) stmt1)
        IStmt whileStmt = new WhileStmt(new NotExp(expression), statement);
        state.getExeStack().push(new CompStmt(statement, whileStmt));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = expression.typecheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typecheck(typeEnv.clone()); // cloned to protect caller's env
            return typeEnv;
        } else {
            throw new MyException("Repeat Until Error: Condition must be boolean.");
        }
    }

    @Override
    public String toString() {
        return "repeat(" + statement + ") until " + expression;
    }
}