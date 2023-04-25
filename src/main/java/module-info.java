module com.panko.apod {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
//    requires org.json;
    requires java.desktop;
    requires java.net.http;
//    requires com.sun.jna;
    requires java.prefs;

    exports com.panko.apod;
    exports com.panko.apod.entity;
    exports com.panko.apod.service;
    exports com.panko.apod.controller;
    exports com.panko.apod.util;

    opens com.panko.apod to javafx.fxml;
    opens com.panko.apod.controller to javafx.fxml;
    opens com.panko.apod.service to javafx.fxml;
    opens com.panko.apod.util to javafx.fxml;
}