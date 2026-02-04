package Model.ProgrState;

import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyDictionary;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.FileTable.FileTable;
import Model.ProgrState.Helper.FileTable.MyFileTable;
import Model.ProgrState.Helper.Heap.IHeap;
import Model.ProgrState.Helper.Heap.MyHeap;
import Model.ProgrState.Helper.List.MyIList;
import Model.ProgrState.Helper.List.MyList;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.Helper.Stack.MyStack;
import Model.Stmt.IStmt;
import Model.Stmt.NopStmt;
import Model.Type.IType;
import Model.Value.IValue;
import Model.Value.StringValue;


import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class PrgState {
    private IHeap<Integer, IValue> heap;
    private static int lastId = 0; // Task 8: Static for ID management , more specific naming
    private int id;

    private MyIStack<IStmt>exeStack;
    private MyIDictionary<String,IValue> symTable;
    private MyIList<IValue> out;
    private FileTable<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram; //good to have cica

    // wrong for forked threads, it must share the same filetable and heap
//    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, IStmt orPrg) {
//        exeStack = stk;
//        symTable = symtbl;
//        out = ot;
//        this.fileTable = new MyFileTable<>();
//        this.heap = new MyHeap();
//        originalProgram = orPrg;// DEEPCOPY recreate entire original program
//        exeStack.push(orPrg);
//        this.id = getNewId(); // Task 8: Assign unique ID
//    }

    private static synchronized int getNewId() { // Task 8: Synchronized for thread safety
        return lastId++;
    }
//for fork constructor
    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot,
                    FileTable<StringValue, BufferedReader> fileTable, IHeap<Integer, IValue> heap) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNewId(); // Task 8: Assign unique ID
    }


    public PrgState(IStmt ex) {
        originalProgram=ex;
        exeStack = new MyStack<IStmt>();
        symTable = new MyDictionary<String, IValue>();
        this.heap = new MyHeap();
        out= new MyList<IValue>();
        fileTable = new MyFileTable<>();
        try {
            ex.typecheck(new MyDictionary<String, IType>());  // verifica tipurile cu un env gol
        } catch (MyException e) {
            throw new RuntimeException("Typecheck failed: " + e.getMessage());
        }
        exeStack.push(ex);
        id=getNewId(); // Task 8
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String,IValue> getSymTable() {
        return symTable;
    }
    public void setSymTable(MyIDictionary<String,IValue> symTable) {
        this.symTable = symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }
    public void setOut(MyIList<IValue> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }
    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public FileTable<StringValue, BufferedReader> getFileTable() { return fileTable; }
    public void setFileTable(FileTable<StringValue, BufferedReader> fileTable) { this.fileTable = fileTable; }

    public IHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Integer, IValue> heap) {
        this.heap = heap;
    }

    public int getId() {
        return id;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("PrgState stack is empty!!!");
        IStmt curr = (IStmt) exeStack.pop();
//        if (curr instanceof NopStmt)
//            return this;
        return curr.execute(this);
    }

     


    public String toString() {
        return "----------------------------------------------- \n " +
                ">>> ProgramState:" + "ID: " + id + // Task 8: Use instance id
                "\n ExeStack: " + exeStack.toString() +
                "\n SymTable: " + symTable.toString() +
                "\n Out: " + out.toString() +
                "\n FileTable: " + fileTable.toString() +
                "\n Heap: " + heap.toString()+
                "\n----------------------------------------------- \n";
    }

}
