module com.ais_r_initial.ais.r.initial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;

    opens com.ais_r_enhanced.ais.r.enhanced to javafx.fxml;
    opens com.ais_r_enhanced.ais.r.enhanced.Controller to javafx.fxml;
    exports com.ais_r_enhanced.ais.r.enhanced;
    exports com.ais_r_enhanced.ais.r.enhanced.Controller;
}
