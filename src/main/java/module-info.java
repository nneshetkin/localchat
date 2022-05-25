module com.example.localchat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.localchat to javafx.fxml;
    exports com.example.localchat;
}