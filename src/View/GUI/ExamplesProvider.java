package View.GUI;

import Model.Exp.*;
import Model.ProgrState.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.*;

import java.awt.*;
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



        //Example repeatAs
        int v; int x; v=3;x=2;
//        (repeat (fork(print(v);x=x-1;print(x));v=v+1) as v==0);
//        nop;nop;nop;nop;nop;nop;nop;
//        print(x)
//        The final Out should be {3,1,2}
        IStmt exRepeatAs = new  CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("x", new IntType()),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(3))),
                                new CompStmt(
                                        new AssignStmt("x", new ValueExp(new IntValue(2))),
                                        new  CompStmt(


                                            new RepeatAsStmt(
                                                    new CompStmt(
                                                    new ForkStmt(
                                                            new CompStmt(
                                                                    new PrintStmt(new VarExp("v")),
                                                                    new CompStmt( new AssignStmt("x", new ArithExp('-', new VarExp("x"), new ValueExp(new IntValue(1)))),
                                                                            new PrintStmt(new VarExp("x")))
                                                            )
                                                    ),
                                                    new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))

                                                    ),
                                                    new RelationalExp( new VarExp("v"), new ValueExp(new IntValue(0)), "==")
                                            ),
                                                new CompStmt(new NopStmt(),
                                                        new CompStmt(new NopStmt(),
                                                                new CompStmt(new NopStmt(),
                                                                        new CompStmt(new NopStmt(),
                                                                                new CompStmt(new NopStmt(),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new PrintStmt(new VarExp("x"))

                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )
        );
        examples.add(exRepeatAs);


        //barrier
        IStmt exBarrier = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                new CompStmt(new VarDeclStmt("id", new IntType()),
                                        new CompStmt(new AssignStmt("id", new ValueExp(new IntValue(5))),
                                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                                new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                        new CompStmt(new NewBarrierStmt("id", new ReadHeapExp(new VarExp("v2"))),
                                                                                new CompStmt(
                                                                                        new ForkStmt(new CompStmt(new AwaitStmt("id"),
                                                                                                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                                        new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new PrintStmt(new ReadHeapExp(new VarExp("v1")))))))),
                                                                                        new CompStmt(
                                                                                                new ForkStmt(new CompStmt(new AwaitStmt("id"),
                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                                new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))))))))))),
                                                                                                new CompStmt(
                                                                                                        new ForkStmt(new CompStmt(new AwaitStmt("id"),
                                                                                                                new PrintStmt(new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(40)))))),
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3")))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(exBarrier);


        return examples;
    }
}