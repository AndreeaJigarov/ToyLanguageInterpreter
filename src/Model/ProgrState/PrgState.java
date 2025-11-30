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
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;


public class PrgState {
    private IHeap<Integer, IValue> heap;
    private static int ID=0;
    private int id;

    private MyIStack<IStmt>exeStack;
    private MyIDictionary<String,IValue> symTable;
    private MyIList<IValue> out;
    private FileTable<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram; //good to have cica

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, IStmt orPrg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        this.fileTable = new MyFileTable<>();
        this.heap = new MyHeap();
        originalProgram = orPrg;// DEEPCOPY recreate entire original program
        exeStack.push(orPrg);
        id=ID++;
    }

    public PrgState(IStmt ex) {
        originalProgram=ex;
        exeStack = new MyStack<IStmt>();
        symTable = new MyDictionary<String, IValue>();
        this.heap = new MyHeap();
        out= new MyList<IValue>();
        fileTable = new MyFileTable<>();
        exeStack.push(ex);
        id=ID++;
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
        if (curr instanceof NopStmt)
            return this;
        return curr.execute(this);
    }


    public String toString() {
        return "----------------------------------------------- \n " +
                ">>> ProgramState:" + "ID: " + ID +
                "\n ExeStack: " + exeStack.toString() +
                "\n SymTable: " + symTable.toString() +
                "\n Out: " + out.toString() +
                "\n FileTable: " + fileTable.toString() +
                "\n Heap: " + heap.toString()+
                "\n----------------------------------------------- \n";
    }

}
