module org.example.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.main to javafx.fxml;
    exports org.example.main;
}