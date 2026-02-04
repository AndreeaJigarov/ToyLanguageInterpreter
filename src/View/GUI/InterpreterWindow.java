package View.GUI;

import Controller.Controller;
import Exceptions.MyException;
import Model.ProgrState.Helper.Dictionary.MyIDictionary;
import Model.ProgrState.PrgState;
import Model.Stmt.IStmt;
import Model.Value.IValue;
import Model.Value.StringValue;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterpreterWindow {

    private final Controller ctrl;

    private final ListView<Integer> prgIds = new ListView<>();
    private final ListView<String> exeStack = new ListView<>();
    private final ListView<IValue> out = new ListView<>();
    private final ListView<String> fileTable = new ListView<>();

    private final TableView<Map.Entry<Integer, IValue>> heapTable = new TableView<>();
    private final TableView<Map.Entry<String, IValue>> symTable = new TableView<>();


    private final TableView<Map.Entry<String, Pair<List<String>, IStmt>>> procTable = new TableView<>();


    private final TextField nrPrgStates = new TextField();
    private final Button oneStepBtn = new Button("Run one step");

    public InterpreterWindow(Controller ctrl) {
        this.ctrl = ctrl;
        Stage stage = new Stage();

        //setup the table
        setupHeapTable();
        setupSymTable();
        setupProcTable();


        //titled panes
        TitledPane prgPane = new TitledPane("PrgState IDs", prgIds);
        TitledPane stackPane = new TitledPane("ExeStack", exeStack);
        TitledPane heapPane = new TitledPane("HeapTable", heapTable);
        TitledPane symPane = new TitledPane("SymTable", symTable);
        TitledPane outPane = new TitledPane("Out", out);
        TitledPane filePane = new TitledPane("FileTable", fileTable);
        TitledPane procPane = new TitledPane("ProcTable", procTable);


        VBox left = new VBox(10, prgPane, stackPane);
        VBox mid  = new VBox(10, heapPane, symPane , procPane);
        VBox right = new VBox(10, outPane, filePane);

        VBox.setVgrow(prgIds, Priority.ALWAYS);
        VBox.setVgrow(exeStack, Priority.ALWAYS);
        VBox.setVgrow(heapTable, Priority.ALWAYS);
        VBox.setVgrow(symTable, Priority.ALWAYS);
        VBox.setVgrow(out, Priority.ALWAYS);
        VBox.setVgrow(fileTable, Priority.ALWAYS);
        VBox.setVgrow(procTable, Priority.ALWAYS);



        // BOTTOM CONTROLS
        nrPrgStates.setEditable(false);
        nrPrgStates.setAlignment(Pos.CENTER);
        nrPrgStates.setMaxWidth(80);

        // i can set style after


        HBox bottomBox = new HBox(10,
                new Label("Nr ProgramStates"),
                nrPrgStates,
                oneStepBtn
        );
        bottomBox.setAlignment(Pos.CENTER);
        right.getChildren().add(bottomBox);

        //root grid
        GridPane root = new GridPane();
        root.setPadding(new Insets(12));
        root.setHgap(12);
        root.setVgap(12);
        root.setAlignment(Pos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(30);
        c1.setHgrow(Priority.ALWAYS);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(40);
        c2.setHgrow(Priority.ALWAYS);

        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(30);
        c3.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(c1, c2, c3);

        root.add(left, 0, 0);
        root.add(mid, 1, 0);
        root.add(right, 2, 0);


        oneStepBtn.setOnAction(e -> runOneStep());
        prgIds.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> updateSelectedPrg());
        updateAll();



        stage.setScene(new Scene(root, 1000, 600));
        stage.setTitle("Interpreter");
        stage.show();

        // Event handlers

    }

    private void setupHeapTable() {
        TableColumn<Map.Entry<Integer, IValue>, Integer> addrCol = new TableColumn<>("address");
        addrCol.setCellValueFactory(e ->
                new javafx.beans.property.SimpleIntegerProperty(e.getValue().getKey()).asObject());


        TableColumn<Map.Entry<Integer, IValue>, IValue> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getValue()));

        heapTable.getColumns().addAll(addrCol, valCol);

    }

    private void setupSymTable() {
        TableColumn<Map.Entry<String, IValue>, String> varCol = new TableColumn<>("variable name");
        varCol.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(p.getValue().getKey()));

        TableColumn<Map.Entry<String, IValue>, IValue> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(p -> new javafx.beans.property.SimpleObjectProperty<>(p.getValue().getValue()));

        symTable.getColumns().addAll(varCol, valCol);

    }

    private void setupProcTable() {
        // The key is a String, the value is a Pair containing the List of params and the Statement
        TableColumn<Map.Entry<String, Pair<List<String>, IStmt>>, String> nameCol = new TableColumn<>("Procedure");
        nameCol.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(
                p.getValue().getKey() + "(" + String.join(", ", p.getValue().getValue().getKey()) + ")"
        ));

        TableColumn<Map.Entry<String, Pair<List<String>, IStmt>>, String> bodyCol = new TableColumn<>("Body");
        bodyCol.setCellValueFactory(p -> new javafx.beans.property.SimpleStringProperty(
                p.getValue().getValue().getValue().toString()
        ));

        procTable.getColumns().clear();
        procTable.getColumns().addAll(nameCol, bodyCol); // This will now resolve correctly
    }



    private void updateAll() {

            List<PrgState> prgs = ctrl.getRepository().getPrgList();

            prgIds.setItems(FXCollections.observableArrayList(
                    prgs.stream().map(PrgState::getId).toList()
            ));

            if (prgs.isEmpty()) {
                nrPrgStates.setText("0");
                prgIds.getItems().clear();
                heapTable.getItems().clear();
                out.getItems().clear();
                fileTable.getItems().clear();
                exeStack.getItems().clear();
                symTable.getItems().clear();
                oneStepBtn.setDisable(true);
                return;
            }
            nrPrgStates.setText(String.valueOf(prgs.size()));
            oneStepBtn.setDisable(false);
            prgIds.getSelectionModel().select(0);
            updateSelectedPrg();


            // Updated: Build heap entries using getKeys() and get()

            heapTable.setItems(FXCollections.observableArrayList(
                    prgs.get(0).getHeap().getContent().entrySet()
                            .stream().map(e -> Map.entry(e.getKey(), e.getValue()))
                            .toList()
            ));

            out.setItems(FXCollections.observableArrayList(
                    prgs.get(0).getOut().getArrayList()
            ));

            fileTable.setItems(FXCollections.observableArrayList(
                    prgs.get(0).getFileTable().getKeys().stream().map(StringValue::getValue).toList()
            ));

            if (prgIds.getSelectionModel().getSelectedItem() == null) {
                prgIds.getSelectionModel().selectFirst();
            }
            updateSelectedPrg();

    }

    private void updateSelectedPrg() {
        Integer id = prgIds.getSelectionModel().getSelectedItem();
        if (id == null) return;

        PrgState ps = ctrl.getRepository().getPrgList().stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElse(null);

        if (ps == null) return;

        // ExeStack: top first (using iterator which reverses)
        List<String> stackItems = new ArrayList<>();
        for (IStmt stmt : ps.getExeStack()) {
            stackItems.add(stmt.toString());
        }
        exeStack.setItems(FXCollections.observableArrayList(stackItems));

        // Update: Get data from getTopSymTable() now
        MyIDictionary<String, IValue> currentSymTable = ps.getTopSymTable();
        symTable.setItems(FXCollections.observableArrayList(
                currentSymTable.getKeys().stream()
                        .map(key -> Map.entry(key, currentSymTable.lookup(key)))
                        .toList()
        ));

        procTable.setItems(FXCollections.observableArrayList(
                ps.getProcTable().getContent().entrySet().stream().toList()
        ));

    }

    private void runOneStep() {
        try {
            ctrl.oneStepGUI();
            updateAll();
        } catch (Exception ignored) {
        }
    }
}