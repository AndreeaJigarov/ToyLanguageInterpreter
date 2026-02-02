package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;

public class CondAssignStmt implements IStmt {
    private final String var;
    private final IExp exp1;
    private final IExp exp2;
    private final IExp exp3;

    public CondAssignStmt(String var, IExp exp1, IExp exp2, IExp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();

        // Requirement 1a: Create if (exp1) then v=exp2 else v=exp3
        IStmt transformation = new IfStmt(
                exp1,
                new AssignStmt(var, exp2),
                new AssignStmt(var, exp3)
        );

        stack.push(transformation); // [cite: 282]
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // Requirement 1a: verify exp1 is bool, and v, exp2, exp3 have same type [cite: 283]
        IType typeVar = typeEnv.lookup(var);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);

        if (type1.equals(new BoolType()) && typeVar.equals(type2) && typeVar.equals(type3)) {
            return typeEnv;
        } else {
            throw new MyException("Conditional Assignment Error: Type mismatch.");
        }
    }

    @Override
    public String toString() {
        return var + " = (" + exp1 + ") ? " + exp2 + " : " + exp3;
    }
}