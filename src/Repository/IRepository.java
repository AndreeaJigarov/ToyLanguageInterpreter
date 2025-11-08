package Repository;
import Exceptions.MyException;
import Model.ProgrState.PrgState;
public interface IRepository {

    public PrgState getCrtPrg();
    public void logPrgStateExec(PrgState state) throws MyException;
    void addProgram(PrgState state);

}
