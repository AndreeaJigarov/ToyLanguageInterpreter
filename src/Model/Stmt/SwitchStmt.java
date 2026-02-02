package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.Exp.RelationalExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.IType;

public class SwitchStmt implements IStmt {
    private final IExp exp;
    private final IExp exp1;
    private final IExp exp2;
    private final IStmt stmt1;
    private final IStmt stmt2;
    private final IStmt stmt3;

    public SwitchStmt(IExp exp, IExp exp1, IStmt stmt1, IExp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();

        // Requirement 1a: Create the nested IF structure
        // if(exp == exp1) then stmt1 else (if(exp == exp2) then stmt2 else stmt3)
        IExp rel1 = new RelationalExp(exp, exp1, "==");
        IExp rel2 = new RelationalExp(exp, exp2, "==");

        IStmt innerIf = new IfStmt(rel2, stmt2, stmt3);
        IStmt outerIf = new IfStmt(rel1, stmt1, innerIf);

        // Push the new statement on the stack [cite: 194]
        stack.push(outerIf);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // Requirement 1a: Verify exp, exp1, and exp2 have the same type
        IType type = exp.typecheck(typeEnv);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);

        if (type.equals(type1) && type.equals(type2)) {
            // Requirement 1a: Typecheck stmt1, stmt2, and stmt3
            // We use clones of the environment to avoid local changes leaking
            stmt1.typecheck(typeEnv.clone());
            stmt2.typecheck(typeEnv.clone());
            stmt3.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("Type mismatch in Switch expressions: all expressions must have the same type.");
        }
    }

    @Override
    public String toString() {
        return String.format("switch(%s) (case %s: %s) (case %s: %s) (default: %s)",
                exp, exp1, stmt1, exp2, stmt2, stmt3);
    }
}