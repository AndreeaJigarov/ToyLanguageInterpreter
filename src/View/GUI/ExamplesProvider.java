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

        //LOCK
        // Logic:
        // new(v1,20);new(v2,30);newLock(x);
        // fork(fork(lock(x);wh(v1,rh(v1)-1);unlock(x));lock(x);wh(v1,rh(v1)+1);unlock(x));
        // fork(fork(wh(v2,rh(v2)+1));wh(v2,rh(v2)+1);unlock(x)); skip... skip; print(rh(v1)); print(rh(v2))

        IStmt exLock = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                new CompStmt(new NewLockStmt("x"),
                                                        new CompStmt(new ForkStmt(
                                                                        new CompStmt(
                                                                                new ForkStmt(new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x")))),
                                                                                new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp('+', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x"))))
                                                                                        ),

                                                                    new CompStmt(new ForkStmt(
                                                                            new CompStmt(
                                                                                    new ForkStmt(new WriteHeapStmt("v2", new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1))))),
                                                                                    new CompStmt(new WriteHeapStmt("v2", new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1)))), new UnlockStmt("x"))
                                                                                        )
                                                                                            ),
                                                                                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt( new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),  new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))), new PrintStmt(new ReadHeapExp(new VarExp("v2")))
                                                                                                    ))))))))))))))))))));
        examples.add(exLock);



        IStmt exLockExam2 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                new CompStmt(new NewLockStmt("x"),
                                                        new CompStmt(
                                                                new ForkStmt(new CompStmt(
                                                                        new ForkStmt(new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp('-',new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x")))),
                                                                        new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), new UnlockStmt("x"))))
                                                                ),
                                                                new CompStmt(
                                                                        new ForkStmt(new CompStmt(
                                                                                new ForkStmt(new WriteHeapStmt("v2", new ArithExp('+',new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1))))),
                                                                                new CompStmt(new WriteHeapStmt("v2", new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1)))), new UnlockStmt("x")))
                                                                        ),
                                                                        new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                                                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))))))))))))))
                                                        )))))));
        examples.add(exLockExam2);

        // Lock example with two independent locks
        IStmt exLockFinal =
                new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new VarDeclStmt("q", new IntType()),
                                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                                new CompStmt(new NewLockStmt("x"),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new ForkStmt(
                                                                                                        new CompStmt(
                                                                                                                new LockStmt("x"),
                                                                                                                new CompStmt(
                                                                                                                        new WriteHeapStmt("v1",
                                                                                                                                new ArithExp('-', new ReadHeapExp(new VarExp("v1")),
                                                                                                                                        new ValueExp(new IntValue(1)))),
                                                                                                                        new UnlockStmt("x")
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompStmt(
                                                                                                        new LockStmt("x"),
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt("v1",
                                                                                                                        new ArithExp('*', new ReadHeapExp(new VarExp("v1")),
                                                                                                                                new ValueExp(new IntValue(10)))),
                                                                                                                new UnlockStmt("x")
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(new NewLockStmt("q"),
                                                                                        new CompStmt(
                                                                                                new ForkStmt(
                                                                                                        new CompStmt(
                                                                                                                new ForkStmt(
                                                                                                                        new CompStmt(
                                                                                                                                new LockStmt("q"),
                                                                                                                                new CompStmt(
                                                                                                                                        new WriteHeapStmt("v2",
                                                                                                                                                new ArithExp('+', new ReadHeapExp(new VarExp("v2")),
                                                                                                                                                        new ValueExp(new IntValue(5)))),
                                                                                                                                        new UnlockStmt("q")
                                                                                                                                )
                                                                                                                        )
                                                                                                                ),
                                                                                                                new CompStmt(
                                                                                                                        new LockStmt("q"),
                                                                                                                        new CompStmt(
                                                                                                                                new WriteHeapStmt("v2",
                                                                                                                                        new ArithExp('*', new ReadHeapExp(new VarExp("v2")),
                                                                                                                                                new ValueExp(new IntValue(10)))),
                                                                                                                                new UnlockStmt("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                                new CompStmt(
                                                                                                                                        new LockStmt("x"),
                                                                                                                                        new CompStmt(
                                                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                                new CompStmt(
                                                                                                                                                        new UnlockStmt("x"),
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new LockStmt("q"),
                                                                                                                                                                new CompStmt(
                                                                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                                                                        new UnlockStmt("q")
                                                                                                                                                                )
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                ))))))
                                                                                )))))))));


        examples.add(exLockFinal);





        return examples;
    }
}