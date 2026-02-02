package Model.Stmt;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.IType;

public class SleepStmt implements IStmt {
    private final int number;

    public SleepStmt(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        // Requirement 2a: pop the statement [cite: 328]
        if (number > 0) {
            // Requirement 2a: if number != 0 push sleep(number-1) back on stack
            MyIStack<IStmt> stack = state.getExeStack();
            stack.push(new SleepStmt(number - 1));
        }
        // If number is 0, we do nothing (already popped), so execution continues
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // No expressions to check, just return the environment
        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }
}