package app.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class CloseMinimizeController implements Initializable {

    @FXML
    void min(MouseEvent event) {
        Node n = (Node) event.getSource();
        Stage s = (Stage)n.getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    void close(MouseEvent e) {
        Node n = (Node) e.getSource();
        Stage s = (Stage)n.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
