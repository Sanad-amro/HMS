module HMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires java.sql;
    requires javafx.swt;

    opens org.example.hms to javafx.fxml;
    opens org.example.hms.controllers to javafx.fxml;
    exports org.example.hms to javafx.graphics;
    exports org.example.hms.controllers to javafx.graphics;
    opens org.example.hms.classes to com.google.gson, javafx.base;


    

}

