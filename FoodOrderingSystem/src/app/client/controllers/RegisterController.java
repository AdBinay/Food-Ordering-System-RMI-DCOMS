package app.client.controllers;

import app.client.interfaces.RegisterInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class RegisterController implements Initializable {
    @FXML
    AnchorPane parent;
    private double x = 0, y = 0;
    private Stage stage;



    boolean validRegister = false;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_email;

    @FXML
    void register(MouseEvent event) {

        String username, password, name, email;

        username = tf_username.getText();
        password = tf_password.getText();
        name = tf_name.getText();
        email = tf_email.getText();

        try{
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            RegisterInterface registerInterface =(RegisterInterface) registry.lookup("register");

            validRegister = registerInterface.handleRegister(name,username, email,password);

            if(validRegister == true) {
            	Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/login.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
                stage.setScene(new Scene(root));
                out.println("\n\n-- [ Registered Successfully] --");
            }
            else {
                out.println("\n\n-- [ Failed Registry --> Cannot login : User Is denied! ] --");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void signin(MouseEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/login.fxml"));
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
