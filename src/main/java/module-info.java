module com.example.adrianomelquiades_brunomorgado_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.adrianomelquiades_brunomorgado_comp228lab5 to javafx.fxml;
    exports com.adrianomelquiades_brunomorgado_comp228lab5;
}