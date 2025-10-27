package Model.Stmt;

import Model.ProgrState.PrgState;
import Model.Type.IType;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;


    @Override
    public PrgState execute(PrgState state) throws Exception {
        return null;
    }
}
