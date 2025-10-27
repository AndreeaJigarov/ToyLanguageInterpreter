package Model.Stmt;

import Model.ProgrState.PrgState;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws Exception {
        return state;
    }
}
