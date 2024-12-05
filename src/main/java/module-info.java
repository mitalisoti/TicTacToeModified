module org.example.tictactoefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.media;

    opens org.example.tictactoefx to javafx.fxml;
    exports org.example.tictactoefx;
}