module org.sakamainty.hibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens org.sakamainty.hibernate to javafx.fxml;
    exports org.sakamainty.hibernate;

    opens org.sakamainty.hibernate.models to com.fasterxml.jackson.databind;
    exports org.sakamainty.hibernate.models;

    opens org.sakamainty.hibernate.models.medecins to com.fasterxml.jackson.databind;
    exports org.sakamainty.hibernate.models.medecins;

    opens org.sakamainty.hibernate.models.patients to com.fasterxml.jackson.databind;
    exports org.sakamainty.hibernate.models.patients;

    opens org.sakamainty.hibernate.models.visites to com.fasterxml.jackson.databind;
    exports org.sakamainty.hibernate.models.visites;

    exports org.sakamainty.hibernate.services;

    opens org.sakamainty.hibernate.services to javafx.fxml;
    exports org.sakamainty.hibernate.controllers;

    opens org.sakamainty.hibernate.controllers to javafx.fxml;
    exports org.sakamainty.hibernate.controllers.medecins;

    opens org.sakamainty.hibernate.controllers.medecins to javafx.fxml;
    exports org.sakamainty.hibernate.controllers.patients;

    opens org.sakamainty.hibernate.controllers.patients to javafx.fxml;
    exports org.sakamainty.hibernate.controllers.visites;

    opens org.sakamainty.hibernate.controllers.visites to javafx.fxml;
}