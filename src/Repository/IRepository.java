package Repository;
import Exceptions.MyException;
import Model.ProgrState.PrgState;

import java.util.List;

public interface IRepository {

    //public PrgState getCrtPrg();
    public void logPrgStateExec(PrgState state) throws MyException;
    void addProgram(PrgState state);
    public List<PrgState> getPrgList(); //added
    public void setPrgList(List<PrgState> list); //added
}
