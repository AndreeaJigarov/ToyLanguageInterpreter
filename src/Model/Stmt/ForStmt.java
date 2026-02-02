package Model.Stmt;

import Exceptions.MyException;
import Model.Exp.IExp;
import Model.Exp.RelationalExp;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Type.IType;
import Model.Type.IntType;

public class ForStmt implements IStmt {
    private final String var;
    private final IExp exp1;
    private final IExp exp2;
    private final IExp exp3;
    private final IStmt stmt;

    public ForStmt(String var, IExp exp1, IExp exp2, IExp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();

        /* Requirement 2a: create the following statement:
           v=exp1; (while(v<exp2) stmt; v=exp3)
        */
        IStmt transform = new CompStmt(
                new AssignStmt(var, exp1),
                new WhileStmt(
                        new RelationalExp(new Model.Exp.VarExp(var), exp2, "<"),
                        new CompStmt(stmt, new AssignStmt(var, exp3))
                )
        );

        stack.push(transform);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        // Verify the loop variable exists and is an integer
        IType typeVar = typeEnv.lookup(var);
        IType type1 = exp1.typecheck(typeEnv);
        IType type2 = exp2.typecheck(typeEnv);
        IType type3 = exp3.typecheck(typeEnv);

        if (typeVar.equals(new IntType()) && type1.equals(new IntType()) &&
                type2.equals(new IntType()) && type3.equals(new IntType())) {

            stmt.typecheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new MyException("For Statement Error: Variable and expressions must be of type Int.");
        }
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + "; " + var + "<" + exp2 + "; " + var + "=" + exp3 + ") " + stmt;
    }
}