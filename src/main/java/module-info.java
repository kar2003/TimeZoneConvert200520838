module com.example.timezoneconvert200520838 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.timezoneconvert200520838 to javafx.fxml;
    exports com.example.timezoneconvert200520838;
}