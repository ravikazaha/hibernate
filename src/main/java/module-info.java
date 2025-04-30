module org.sakamainty.hibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens org.sakamainty.hibernate to javafx.fxml;
    exports org.sakamainty.hibernate;

    opens org.sakamainty.hibernate.models to com.fasterxml.jackson.databind;
    opens org.sakamainty.hibernate.lib to com.fasterxml.jackson.databind;

    exports org.sakamainty.hibernate.models;
    exports org.sakamainty.hibernate.lib;
}