package View.GUI;

import Model.Exp.*;
import Model.ProgrState.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.*;

import java.util.ArrayList;
import java.util.List;

public class ExamplesProvider {
    public static List<IStmt> getAll() {
        List<IStmt> examples = new ArrayList<>();

        // ex1
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        examples.add(ex1);

        // ex2
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                        new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
        examples.add(ex2);

        // ex3
        IStmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
        examples.add(ex3);

        // File operations (testStmt)
        IStmt testStmt = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CloseRFileStmt(new VarExp("varf"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(testStmt);

        // Heap allocation
        IStmt exHeapAlloc = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                )
                        )
                )
        );
        examples.add(exHeapAlloc);

        // Heap read
        IStmt exHeapRead = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                )
                        )
                )
        );
        examples.add(exHeapRead);

        // Heap write
        IStmt exHeapWrite = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))
                                )
                        )
                )
        );
        examples.add(exHeapWrite);

        // GC example (assuming it's similar to heap, adjust if needed)
        IStmt exGC = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );
        examples.add(exGC);

        // While
        IStmt exWhile = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v",
                                                        new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))
                                                )
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        examples.add(exWhile);

        // Fork
        IStmt exFork = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(exFork);

        // SWITCH
        IStmt exSwitch = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("c", new IntType()),
                                new CompStmt(
                                        new AssignStmt("a", new ValueExp(new IntValue(1))),
                                        new CompStmt(
                                                new AssignStmt("b", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new AssignStmt("c", new ValueExp(new IntValue(5))),
                                                        new CompStmt(
                                                                new SwitchStmt(
                                                                        new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))),
                                                                        new ArithExp('*', new VarExp("b"), new VarExp("c")),
                                                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b"))),
                                                                        new ValueExp(new IntValue(10)),
                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))), new PrintStmt(new ValueExp(new IntValue(200)))),
                                                                        new PrintStmt(new ValueExp(new IntValue(300)))
                                                                ),
                                                                new PrintStmt(new ValueExp(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(exSwitch);

        //FOR
        // v=20; (for(v=0; v<3; v=v+1) fork(print(v); v=v+1)); print(v*10)
        IStmt exFor = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new Model.Exp.ValueExp(new Model.Value.IntValue(20))),
                        new CompStmt(
                                new ForStmt("v",
                                        new Model.Exp.ValueExp(new Model.Value.IntValue(0)),
                                        new Model.Exp.ValueExp(new Model.Value.IntValue(3)),
                                        new Model.Exp.ArithExp('+', new Model.Exp.VarExp("v"), new Model.Exp.ValueExp(new Model.Value.IntValue(1))),
                                        new ForkStmt(new CompStmt(
                                                new PrintStmt(new Model.Exp.VarExp("v")),
                                                new AssignStmt("v", new Model.Exp.ArithExp('+', new Model.Exp.VarExp("v"), new Model.Exp.ValueExp(new Model.Value.IntValue(1))))
                                        ))
                                ),
                                new PrintStmt(new Model.Exp.ArithExp('*', new Model.Exp.VarExp("v"), new Model.Exp.ValueExp(new Model.Value.IntValue(10))))
                        )
                )
        );
        examples.add(exFor);

        return examples;
    }
}