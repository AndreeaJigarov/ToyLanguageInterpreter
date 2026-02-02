package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.ValueExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Value.IntValue;

public class WaitStmt implements IStmt {
    private final int number;

    public WaitStmt(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack(); // The statement is already popped by the PrgState.oneStep() logic [cite: 359]

        if (number != 0) {
            // Requirement 2a: push (print(number); wait(number-1)) on the stack
            IStmt transformation = new CompStmt(
                    new PrintStmt(new ValueExp(new IntValue(number))),
                    new WaitStmt(number - 1)
            );
            stack.push(transformation);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // No variables or expressions to check against the environment
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wait(" + number + ")";
    }
}