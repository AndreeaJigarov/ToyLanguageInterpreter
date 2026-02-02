package View.GUI;

import Controller.Controller;
import Model.ProgrState.Helper.Dictionary.MyDictionary;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.FileTable.FileTable;
import Model.ProgrState.Helper.FileTable.MyFileTable;
import Model.ProgrState.Helper.Heap.MyHeap;
import Model.ProgrState.Helper.List.MyList;
import Model.ProgrState.Helper.Stack.MyIStack;
import Model.ProgrState.Helper.Stack.MyStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.StringValue;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;

public class ProgramSelectorWindow {

    public ProgramSelectorWindow(Stage stage) { //the stage - root window
        ListView<IStmt> listView = new ListView<>(); //
        listView.setItems(FXCollections.observableArrayList(ExamplesProvider.getAll()));

        Button runBtn = new Button("Run selected program");


        runBtn.setOnAction(e -> {
            IStmt stmt = listView.getSelectionModel().getSelectedItem();
            if (stmt == null) {
                new Alert(Alert.AlertType.ERROR, "Select a program first").show();
                return;
            }


            //(MyIStack<IStmt> stk, MyIDictionary<String, IValue > symtbl, MyIList<IValue> ot,
               //     FileTable< StringValue, BufferedReader > fileTable, IHeap<Integer, IValue> heap

            PrgState prg = new PrgState(stmt);
            try {
                Repository repo = new Repository(prg, "log.txt");
                Controller ctrl = new Controller(repo);
                new InterpreterWindow(ctrl);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
        });

        VBox root = new VBox(10, listView, runBtn); // Group root = new Group
        stage.setScene(new Scene(root, 600, 400)); // these are all the must haves in the main app
        stage.setTitle("Select program");
        stage.show();
    }
}