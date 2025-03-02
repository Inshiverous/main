module org.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.xml.dom;
    requires java.sql;


    opens org.example.main to javafx.fxml;
    exports org.example.main;
}