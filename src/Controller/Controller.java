package Controller;

import Exceptions.MyException;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Repository.IRepository;

public class Controller  implements IController {
    @Override
    public PrgState oneStep(PrgState prgState) throws MyException {
        MyIStack<IStmt> stk = prgState.getExeStack();
        if(stk.isEmpty())throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(prgState);
    }

    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void allStep() throws MyException {
        PrgState prg = repository.getCrtPrg();
        System.out.println("Initial state:");
        System.out.println(prg);
        repository.logPrgStateExec(prg); //added
        while(!prg.getExeStack().isEmpty()){
            prg = oneStep(prg);
            System.out.println("After one step:");
            System.out.println(prg);
            repository.logPrgStateExec(prg); //added
        }
    }




}
