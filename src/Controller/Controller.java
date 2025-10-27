package Controller;

import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Repository.IRepository;

public class Controller  implements IController {
    @Override
    public PrgState oneStep(PrgState prgState) throws Exception {
        MyIStack<IStmt> stk = prgState.getExeStack();
        if(stk.isEmpty())throw new Exception("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(prgState);
    }

    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void allStep() throws Exception {
        PrgState prg = repository.getCrtPrg();
        //here you can display the prg state
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            //here u can display the prg state
        }
    }




}
