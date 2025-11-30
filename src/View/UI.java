package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.Exp.*;
import Model.ProgrState.PrgState;
import Model.Stmt.*;
import Model.Type.*;
import Model.Value.*;
import Repository.Repository;

import java.util.Scanner;

public class UI {

    public UI() {
    }

    public void printMenu(){
        System.out.println("Menu");
        System.out.println("-------------");
        System.out.println("1. Run program: \n" + "int v; v=2;Print(v) ");
        System.out.println("2. Run program: \n" + "int a;int b; a=2+3*5;b=a+1;Print(b);");
        System.out.println("3. Run program: \n" + "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v);");
        System.out.println("5. Run program: \n" + "string varf;\n" +
                "varf=\"test.in\";\n" +
                "openRFile(varf);\n" +
                "int varc;\n" +
                "readFile(varf,varc);print(varc);\n" +
                "readFile(varf,varc);print(varc)\n" +
                "closeRFile(varf)\n");
        System.out.println("4. Exit");
    }

    public void executeOption( int option, String path) throws MyException {
        switch(option){
            case 1:
                IStmt ex1= new CompStmt
                        (new VarDeclStmt("v",new IntType()), new CompStmt
                                (new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
                PrgState prg1 = new PrgState(ex1);
                Repository repository = new Repository(prg1, path);
                Controller controller = new Controller(repository);
                controller.allStep();
                break;
            case 2:
                IStmt ex2 = new CompStmt(
                                new VarDeclStmt("a",new IntType()),
                                new CompStmt(new VarDeclStmt("b",new IntType()),
                                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                                                                new ArithExp('*',new ValueExp(new IntValue(3)),
                                                                                        new ValueExp(new IntValue(5))))),
                                        new CompStmt(new AssignStmt("b",
                                                                new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                                                new PrintStmt(new VarExp("b"))))));
                PrgState prg2 = new PrgState(ex2);
                Repository repository2 = new Repository(prg2, path);
                Controller controller2 = new Controller(repository2);
                controller2.allStep();
                break;
            case 3:
                IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                                IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                                VarExp("v"))))));
                PrgState prg3 = new PrgState(ex3);
                Repository repository3 = new Repository(prg3, path);
                Controller controller3 = new Controller(repository3);
                controller3.allStep();
                break;
            case 4:
                return;
            case 5:
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
                Repository repository4 = new Repository(prg4, path);
                Controller controller4 = new Controller(repository4);
                controller4.allStep();
                break;
            default:
                throw new MyException("Not a valid input, ntntntnt!😒");
        }
    }

    public void run(){
        int option = 0;
        System.out.println("Please enter file path to save to > ");
        Scanner sc1 = new Scanner(System.in);
        String path = sc1.nextLine();

        while(option != 4){
            printMenu();
            try{
                Scanner sc = new Scanner(System.in);
                option=Integer.parseInt(sc.nextLine());
                executeOption(option, path);

            }catch (Exception ex){
                System.err.println(ex.getMessage());
                return;
            }
        }
        System.out.println("Thank u🦝🦝🦝");
        System.exit(0);


    }


}

