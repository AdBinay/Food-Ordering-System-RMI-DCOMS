package app.client.controllers;

import app.client.interfaces.LoginInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import static java.lang.System.*;

public class LoginController implements Initializable {

    @FXML
    AnchorPane parent;
    private double x = 0, y = 0;
    private Stage stage;

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    void login(MouseEvent event) {

        Integer role;

        String username, password;

        username = tf_username.getText();
        password = tf_password.getText();

        try {
            //Getting the RMI registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);

            //using lookup function to fitch the desired method from the remote object
            LoginInterface loginInterface =(LoginInterface)registry.lookup("login");

            //Invoking the desired method using the obtained remote object
            role = loginInterface.obtainLogin(username, password);
            System.out.println(role);

            if(role == 0) {
                Thread.sleep(1000);
                Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/admin.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(root));

                out.println("\n\n-- [ Successful Admin Login: Admin Is logged in! ] --");
            }
            else if(role == 1) {
                Thread.sleep(1000);
                Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/Customer.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(root));

                out.println("\n\n-- [ Successful Student Login: Student Is logged in! ] --");
            }
            else {
                out.println("\n\n-- [ Login Failed: User Is denied! ] --");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void signup(MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/register.fxml"));
        Node node = (Node) e.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDragable();
    }

    private void makeDragable(){
        parent.setOnMousePressed(((event)->{
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        parent.setOnMouseDragged(((event)->{
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);

        }));
    }
}
