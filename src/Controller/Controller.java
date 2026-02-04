package Controller;

import Exceptions.MyException;
import Model.ProgrState.Helper.List.MyIList;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.concurrent.Callable;

public class Controller  implements IController {
    IRepository repository;
    private ExecutorService executor; // Task 12

    // -- removeeeeee task 7
//    @Override
//    public PrgState oneStep(PrgState prgState) throws MyException {
//        MyIStack<IStmt> stk = prgState.getExeStack();
//        if(stk.isEmpty())throw new MyException("prgstate stack is empty");
//        IStmt crtStmt = stk.pop();
//
//        crtStmt.execute(prgState);  // Execute modifies prgState in place
//        return prgState;  // Return the updated state
//    }

    // Task 10
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        // Log each program exactly ONCE before execution
        for (PrgState prg : prgList) {
            repository.logPrgStateExec(prg);
        }

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());

        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try { return future.get(); }
                        catch (Exception e) { throw new RuntimeException(e); }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new MyException("Interrupted: " + e.getMessage());
        }

        prgList.addAll(newPrgList);

        // Log each program exactly ONCE after execution
        for (PrgState prg : prgList) {
            repository.logPrgStateExec(prg);
        }
        repository.setPrgList(prgList);
    }

    public Controller(IRepository repository) {
        this.repository = repository;
    }



    void conservativeGarbageCollector(List<PrgState> prgList) {

        // all addresses from all symtables
        List<Integer> addresses = prgList.stream()
                .flatMap(prg -> prg.getSymTable().getValues().stream())
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .toList();

        // filter heap
        Map<Integer, IValue> newHeap = prgList.get(0).getHeap().getContent().entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // shared heap
        prgList.get(0).getHeap().setContent(newHeap);
    }
    // Task 13/15: Rewritten allStep
    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2); // Task 12/15
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (!prgList.isEmpty()) {

            if (prgList.size() <= 1) {
                // skip gc when single threaded - most student projects do this
            } else {
                conservativeGarbageCollector(prgList);
            }
            //conservativeGarbageCollector(prgList);

            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }


    // Task 16: Updated garbage collector (handles multiple symTables)
    private Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//    public static List<Integer> getAllAddresses(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
//        List<Integer> addresses = new ArrayList<>();
//
//        // Start with addresses from SymTable
//        symTableValues.stream()
//                .filter(v -> v instanceof RefValue)
//                .map(v -> ((RefValue) v).getAddress())
//                .forEach(addresses::add);
//
//        // Follow references inside the heap
//        boolean changed = true;
//        while (changed) {
//            changed = false;
//            List<Integer> newAddresses = new ArrayList<>();
//            for (Map.Entry<Integer, IValue> entry : heap.entrySet()) {
//                int addr = entry.getKey();
//                IValue value = entry.getValue();
//                if (value instanceof RefValue refValue) {
//                    int targetAddr = refValue.getAddress();
//                    if (addresses.contains(addr) && !addresses.contains(targetAddr)) {
//                        newAddresses.add(targetAddr);
//                        changed = true;
//                    }
//                }
//            }
//            addresses.addAll(newAddresses);
//        }
//
//        return addresses;
//    }
//
//

    // Task 16: Updated to handle list of symTable values
    public static List<Integer> getAllAddresses(List<Collection<IValue>> symTablesValues, Map<Integer, IValue> heap) {
        List<Integer> addresses = new ArrayList<>();

        // Addresses from all SymTables
        addresses.addAll(symTablesValues.stream()
                .flatMap(Collection::stream)
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .toList());

        // Follow references in heap
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

    public IRepository getRepository() {
        return repository;
    }

    public void oneStepGUI() throws MyException {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(2);
        }

        repository.setPrgList(
                removeCompletedPrg(repository.getPrgList())
        );

        if (repository.getPrgList().isEmpty()) {
            executor.shutdownNow();
            executor = null;
            return;
        }

        oneStepForAllPrg(repository.getPrgList());
    }
}
