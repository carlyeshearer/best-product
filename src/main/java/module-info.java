module com.example.bestproduct {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bestproduct to javafx.fxml;
    exports com.example.bestproduct;
}