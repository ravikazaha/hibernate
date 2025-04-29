module org.sakamainty.hibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;


    opens org.sakamainty.hibernate to javafx.fxml;
    exports org.sakamainty.hibernate;
}