package View.GUI;

import Controller.Controller;
import Model.ProgrState.Helper.Dictionary.MyDictionary;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.Helper.ProcTable.IProcTable;
import Model.ProgrState.Helper.ProcTable.MyProcTable;
import Model.ProgrState.Helper.Stack.MyStack;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgramSelectorWindow {

    public ProgramSelectorWindow(Stage stage) {
        ListView<IStmt> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(ExamplesProvider.getAll()));

        Button runBtn = new Button("Run selected program");

        runBtn.setOnAction(e -> {
            IStmt stmt = listView.getSelectionModel().getSelectedItem();
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();

            if (stmt == null) {
                new Alert(Alert.AlertType.ERROR, "Select a program first").show();
                return;
            }

            try {
                // 1. Requirement B: Define the Procedure Table [cite: 11]
                // We use your static helper to get the procedures if the procedure example is selected
                // Assuming the procedure example is at a specific index like your friend's code
                IProcTable procTable;
                if (stmt.toString().contains("call sum")) {
                    procTable = ExamplesProvider.getProcTableForExample(); //
                } else {
                    procTable = new MyProcTable();
                }

                // 2. Requirement A: Initialize SymTableStack with one empty frame [cite: 7, 8]
                MyStack<MyIDictionary<String, IValue>> symStack = new MyStack<>();
                symStack.push(new MyDictionary<>());

                // 3. Create the ProgramState using the specific constructor for shared structures
                // Use the constructor that accepts all ADTs to ensure ProcTable is shared
                PrgState prg = new PrgState(
                        new MyStack<>(),
                        symStack,
                        new Model.ProgrState.Helper.List.MyList<>(),
                        new Model.ProgrState.Helper.FileTable.MyFileTable<>(),
                        new Model.ProgrState.Helper.Heap.MyHeap(),
                        procTable
                );

                // Add the initial statement to the ExeStack
                prg.getExeStack().push(stmt);

                // 4. Launch Window
                Repository repo = new Repository(prg, "log.txt");
                Controller ctrl = new Controller(repo);
                new InterpreterWindow(ctrl);

            } catch (Throwable ex) {
                // Catches initialization/Typecheck errors that prevent the window from opening
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fatal Error");
                alert.setHeaderText("Window failed to launch");
                alert.setContentText(ex.getMessage() != null ? ex.getMessage() : ex.toString());
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, listView, runBtn);
        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Select program");
        stage.show();
    }
}