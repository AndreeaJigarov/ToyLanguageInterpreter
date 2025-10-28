package Model.ProgrState;

import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.List.MyIList;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.Stmt.IStmt;
import Model.Stmt.NopStmt;
import Model.Value.IValue;



public class PrgState {
    private static int ID=0;
    private int id;

    private MyIStack<IStmt>exeStack;
    private MyIDictionary<String,IValue> symTable;
    private MyIList<IValue> out;
    private IStmt originalProgram; //good to have cica

    PrgState(MyIStack<IStmt> stk, MyIDictionary<String,IValue> symtbl, MyIList<IValue> ot, IStmt orPrg) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = orPrg;// DEEPCOPY recreate entire original program
        stk.push(orPrg);
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

    public int getId() {
        return id;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws Exception {
        if(exeStack.isEmpty())
            throw new Exception("PrgState stack is empty!!!");
        IStmt curr = (IStmt) exeStack.pop();
        if (curr instanceof NopStmt)
            return this;
        return curr.execute(this);
    }


    public String toString(

    ) {
        return "----------------------------------------------- \n " +
                "ID: " + ID +
                "\n ExeStack: " + exeStack.toString() +
                "\n SymTable: " + symTable.toString() +
                "\n Out: " + out.toString() +
                "\n----------------------------------------------- \n";
    }

}
