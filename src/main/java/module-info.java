module com.example.astronomy_picture_of_the_day {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires java.net.http;
    requires com.sun.jna;

    exports com.panko.astronomy_picture_of_the_day;
    exports com.panko.astronomy_picture_of_the_day.entity;
    exports com.panko.astronomy_picture_of_the_day.service;
    exports com.panko.astronomy_picture_of_the_day.controller;

    opens com.panko.astronomy_picture_of_the_day to javafx.fxml;
    opens com.panko.astronomy_picture_of_the_day.controller to javafx.fxml;
    opens com.panko.astronomy_picture_of_the_day.service to javafx.fxml;
    exports com.panko.astronomy_picture_of_the_day.util;
    opens com.panko.astronomy_picture_of_the_day.util to javafx.fxml;
}