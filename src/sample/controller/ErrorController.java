package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorController {
    public Text errorMessage;
    public Button btnClose;

    public void setErrorText(String text) {
        errorMessage.setText(text);
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
