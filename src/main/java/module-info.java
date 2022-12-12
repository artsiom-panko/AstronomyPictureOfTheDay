module com.example.astronomy_picture_of_the_day {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;
    requires java.net.http;
    requires com.sun.jna;


    opens com.panko.astronomy_picture_of_the_day to javafx.fxml;
    exports com.panko.astronomy_picture_of_the_day;
    exports com.panko.astronomy_picture_of_the_day.controller;
    opens com.panko.astronomy_picture_of_the_day.controller to javafx.fxml;
}