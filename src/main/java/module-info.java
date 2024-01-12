module com.example.timezoneconvert200520838 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires async.http.client.netty.utils;
    requires async.http.client;
    requires com.fasterxml.jackson.databind;
    requires json.simple;

    opens com.example.timezoneconvert200520838 to javafx.fxml;
    exports com.example.timezoneconvert200520838;
    exports com.example.timezoneconvert200520838.controller;
    exports com.example.timezoneconvert200520838.model;
    opens com.example.timezoneconvert200520838.controller to javafx.fxml;
}