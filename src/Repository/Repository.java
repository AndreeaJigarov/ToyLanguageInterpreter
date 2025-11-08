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
import java.util.LinkedList;

public class Repository implements IRepository {
    private LinkedList<PrgState> prgStates;
    private PrgState crtPrgState;
    private String logFilePath;

    public Repository() {
        this.prgStates = new LinkedList<>() ;
    }

    public Repository(PrgState prg, String logPath) throws MyException {
        this.crtPrgState = prg;
        try {
            PrintWriter testPath = new PrintWriter(new BufferedWriter(new FileWriter(logPath, true)));
        } catch (IOException e) {
            throw new MyException("Cannot open log file: " + logPath +"\n" + e.getMessage());
        }
        this.prgStates = new LinkedList<>();
        this.addProgram(this.crtPrgState);
        this.logFilePath = logPath;
    }

    @Override
    public void addProgram(PrgState state) {
        this.prgStates.add(state);
    }

    @Override
    public PrgState getCrtPrg(){
            PrgState current = prgStates.getFirst();
            prgStates.removeFirst();
            return current;
        }




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

//            logFile.println("FileTable:");
//            for (StringValue fileName : state.getFileTable().getKeys()) {
//                logFile.println(fileName.getVal());
//            }
            logFile.println(); // blank line between states
        } catch (IOException e) {
            throw new MyException("Logging failed: " + e.getMessage());
        }
    }



}