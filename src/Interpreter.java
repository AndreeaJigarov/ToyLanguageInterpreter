import Controller.Controller;
import Exceptions.MyException;
import Model.Exp.*;
import Model.ProgrState.PrgState;
import Model.Stmt.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.RefType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

import java.util.Scanner;

public class Interpreter {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter log file path:");
//        String path = scanner.nextLine();
        try {
            IStmt ex1 = new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))
                    )
            );
            PrgState prg1 = new PrgState(ex1);
            Repository repository1 = new Repository(prg1, "saveFile1.txt");
            Controller controller1 = new Controller(repository1);

            
            IStmt ex2 = new CompStmt(
                    new VarDeclStmt("a", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("b", new IntType()),
                            new CompStmt(
                                    new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                            new ArithExp('*', new ValueExp(new IntValue(3)),
                                                    new ValueExp(new IntValue(5))))),
                                    new CompStmt(
                                            new AssignStmt("b",
                                                    new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                            new PrintStmt(new VarExp("b"))
                                    )
                            )
                    )
            );
            PrgState prg2 = new PrgState(ex2);
            Repository repository2 = new Repository(prg2, "saveFile2.txt");
            Controller controller2 = new Controller(repository2);

            IStmt ex3 = new CompStmt(
                    new VarDeclStmt("a", new BoolType()),
                    new CompStmt(
                            new VarDeclStmt("v", new IntType()),
                            new CompStmt(
                                    new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(
                                            new IfStmt(new VarExp("a"),
                                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                            new PrintStmt(new VarExp("v"))
                                    )
                            )
                    )
            );
            PrgState prg3 = new PrgState(ex3);
            Repository repository3 = new Repository(prg3, "saveFile3.txt");
            Controller controller3 = new Controller(repository3);

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
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("varc")),
                                                                            new CloseRFileStmt(new VarExp("varf"))
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            PrgState prg4 = new PrgState(testStmt);
            Repository repository4 = new Repository(prg4, "saveFile4.txt");
            Controller controller4 = new Controller(repository4);

            // New examples from Lab7.pdf

            // Example 1: Heap Allocation
            // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
            IStmt exHeapAlloc = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(
                            new NewStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(
                                            new NewStmt("a", new VarExp("v")),
                                            new CompStmt(
                                                    new PrintStmt(new VarExp("v")),
                                                    new PrintStmt(new VarExp("a"))
                                            )
                                    )
                            )
                    )
            );
            PrgState prgHeapAlloc = new PrgState(exHeapAlloc);
            Repository repositoryHeapAlloc = new Repository(prgHeapAlloc, "saveFile5.txt");
            Controller controllerHeapAlloc = new Controller(repositoryHeapAlloc);

            // Example 2: Heap Reading
            // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
            IStmt exHeapRead = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(
                            new NewStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                    new CompStmt(
                                            new NewStmt("a", new VarExp("v")),
                                            new CompStmt(
                                                    new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                    new PrintStmt(
                                                            new ArithExp('+',
                                                                    new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                                    new ValueExp(new IntValue(5))
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            PrgState prgHeapRead = new PrgState(exHeapRead);
            Repository repositoryHeapRead = new Repository(prgHeapRead, "saveFile6.txt");
            Controller controllerHeapRead = new Controller(repositoryHeapRead);

            // Example 3: Heap Writing
            // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
            IStmt exHeapWrite = new CompStmt(
                    new VarDeclStmt("v", new RefType(new IntType())),
                    new CompStmt(
                            new NewStmt("v", new ValueExp(new IntValue(20))),
                            new CompStmt(
                                    new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                    new CompStmt(
                                            new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(
                                                    new ArithExp('+',
                                                            new ReadHeapExp(new VarExp("v")),
                                                            new ValueExp(new IntValue(5))
                                                    )
                                            )
                                    )
                            )
                    )
            );
            PrgState prgHeapWrite = new PrgState(exHeapWrite);
            Repository repositoryHeapWrite = new Repository(prgHeapWrite, "saveFile7.txt");
            Controller controllerHeapWrite = new Controller(repositoryHeapWrite);

            // Example 4: Garbage Collector test
            // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
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
            PrgState prgGC = new PrgState(exGC);
            Repository repositoryGC = new Repository(prgGC, "saveFile8.txt");
            Controller controllerGC = new Controller(repositoryGC);

            // Example 5: While Statement
            // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
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
            PrgState prgWhile = new PrgState(exWhile);
            Repository repositoryWhile = new Repository(prgWhile, "saveFile9.txt");
            Controller controllerWhile = new Controller(repositoryWhile);


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
            PrgState prgFork = new PrgState(exFork);
            Repository repositoryFork = new Repository(prgFork, "saveFile10.txt");
            Controller controllerFork = new Controller(repositoryFork);

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "exit")); // Changed to 0 for exit
            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
            menu.addCommand(new RunExample("3", ex3.toString(), controller3));
            menu.addCommand(new RunExample("4", testStmt.toString(), controller4));
            menu.addCommand(new RunExample("5", exHeapAlloc.toString(), controllerHeapAlloc));
            menu.addCommand(new RunExample("6", exHeapRead.toString(), controllerHeapRead));
            menu.addCommand(new RunExample("7", exHeapWrite.toString(), controllerHeapWrite));
            menu.addCommand(new RunExample("8", exGC.toString(), controllerGC));
            menu.addCommand(new RunExample("9", exWhile.toString(), controllerWhile));
            menu.addCommand(new RunExample("10", exFork.toString(), controllerFork));
            menu.show();

        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

    }
}