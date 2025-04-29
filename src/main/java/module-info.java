module org.sakamainty.hibernate {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.sakamainty.hibernate to javafx.fxml;
    exports org.sakamainty.hibernate;
}