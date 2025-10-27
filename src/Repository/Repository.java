package Repository;
import Model.ProgrState.PrgState;
public class Repository implements IRepository {
    private PrgState prgState;

    public PrgState getCrtPrg(){
        return prgState;
    }
}
