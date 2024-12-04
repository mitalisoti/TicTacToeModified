module org.example.tictactoefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.tictactoefx to javafx.fxml;
    exports org.example.tictactoefx;
}