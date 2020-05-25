module objectgenerator {

    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.controls;
    requires javafx.base;
    requires java.sql;
    requires gson;

    opens com.andrewhun.object;
    /*opens com.andrewhun.object.objectcreation;
    opens com.andrewhun.object.objectcreation.startingpage;
    opens com.andrewhun.object.objectcreation.variables;
    opens com.andrewhun.object.objectcreation.methods;*/
    opens com.andrewhun.object.controllers;
    opens com.andrewhun.object.models;
    opens com.andrewhun.object.util;
    opens com.andrewhun.object.services;
}