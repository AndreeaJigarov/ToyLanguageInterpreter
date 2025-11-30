package Controller;

import Exceptions.MyException;
import Model.ProgrState.Helper.List.MyIList;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller  implements IController {
    @Override
    public PrgState oneStep(PrgState prgState) throws MyException {
        MyIStack<IStmt> stk = prgState.getExeStack();
        if(stk.isEmpty())throw new MyException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();

        crtStmt.execute(prgState);  // Execute modifies prgState in place
        return prgState;  // Return the updated state
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
            repository.logPrgStateExec(prg);
            //added
            var safeAddresses = getAllAddresses(
                    prg.getSymTable().getValues(),
                    prg.getHeap().getContent()
            );

            prg.getHeap().setContent(
                    prg.getHeap().getContent().entrySet().stream()
                            .filter(e -> safeAddresses.contains(e.getKey()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );

            repository.logPrgStateExec(prg);
        }
    }
    public static List<Integer> getAllAddresses(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        List<Integer> addresses = new ArrayList<>();

        // Start with addresses from SymTable
        symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .forEach(addresses::add);

        // Follow references inside the heap
        boolean changed = true;
        while (changed) {
            changed = false;
            List<Integer> newAddresses = new ArrayList<>();
            for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
                int addr = entry.getKey();
                IValue value = entry.getValue();
                if (value instanceof RefValue refValue) {
                    int targetAddr = refValue.getAddress();
                    if (addresses.contains(addr) && !addresses.contains(targetAddr)) {
                        newAddresses.add(targetAddr);
                        changed = true;
                    }
                }
            }
            addresses.addAll(newAddresses);
        }

        return addresses;
    }




}
