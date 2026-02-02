package View.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application { //in turn Application extends Object
    @Override
    public void start(Stage stage) { // start is abstract in Application and must be overriden
        new ProgramSelectorWindow(stage);
    }

    public static void main(String[] args) {
        launch(args); // creates a new object of class application , launch is static
    }
}