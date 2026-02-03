package Repository;
import Exceptions.MyException;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;// Task 1: List of PrgStates (threads)
    //private PrgState crtPrgState;
    private String logFilePath;

    public Repository() {
        this.prgStates = new ArrayList<>() ;
    }

    public Repository(PrgState prg, String logPath) throws MyException {
        //this.crtPrgState = prg;
        try {
            PrintWriter testPath = new PrintWriter(new BufferedWriter(new FileWriter(logPath, true)));
        } catch (IOException e) {
            throw new MyException("Cannot open log file: " + logPath +"\n" + e.getMessage());
        }
        this.prgStates = new ArrayList<>();
        this.addProgram(prg); // initial program , it was crt prg then
        this.logFilePath = logPath;
    }

    @Override
    public void addProgram(PrgState state) {
        this.prgStates.add(state);
    }

    // Task 2: New method
    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    // Task 3: New method
    @Override
    public void setPrgList(List<PrgState> list) {
        this.prgStates = list;
    }

//    @Override
//    public PrgState getCrtPrg(){
//            PrgState current = prgStates.getFirst();
//            prgStates.removeFirst();
//            return current;
//        } -- removed




//    @Override
//    public void logPrgStateExec() throws MyException {
//        try {
//            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
//            logFile.println(crtPrgState.toString());
//            logFile.flush();
//            if (crtPrgState.getExeStack().isEmpty()) {
//                logFile.close();
//            }
//        } catch (Exception e) {
//            throw new MyException("Logging failed: " + e.getMessage());
//        }
//    }

    @Override
    public void logPrgStateExec(PrgState state) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true))))
        {
            logFile.println(">>> ProgramState ID: " + state.getId()); // Task 8: Print ID first
            logFile.println("ExeStack:");
            for (IStmt stmt : state.getExeStack()) {
                logFile.println(stmt);  // toString() should return infix form
            }
            logFile.println("SymTable:");
            for (String var : state.getSymTable().getKeys()) {
                IValue val = state.getSymTable().lookup(var);
                logFile.println(var + " --> " + val);
            }
            logFile.println("Out:");
            for (IValue val : state.getOut()) {
                logFile.println(val);
            }

            logFile.println("FileTable:");
            for (StringValue fileName : state.getFileTable().getKeys()) {
                logFile.println(fileName.getValue());
            }

            logFile.println("Heap:");
            logFile.println(state.getHeap().toString());

            logFile.println("BarierTable:");
            logFile.println(state.getBarrierTable().toString());

            logFile.println();
        } catch (IOException e) {
            throw new MyException("Logging failed: " + e.getMessage());
        }
    }



}