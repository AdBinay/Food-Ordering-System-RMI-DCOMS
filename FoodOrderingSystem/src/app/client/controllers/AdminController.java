package app.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import app.models.Order;

public class AdminController implements Initializable {

    @FXML
    private BorderPane borderpane;
    private double x = 0, y = 0;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDragable();
    }

    @FXML
    public void createAdmin() throws IOException {
        bladeLoad("createAdmin");
    }

    @FXML
    public void createCustomer() throws IOException {
        bladeLoad("createCustomer");
    }

    @FXML
    public void changePassword() throws IOException {
        bladeLoad("changePassword");
    }

    @FXML
    public void addNewFood() throws IOException {
        bladeLoad("addNewFood");
    }

    @FXML
    public void viewFood() throws IOException {
    	FXMLLoader p = bladeLoad("viewFood");
    	FoodController foodController = p.getController();
        foodController.setupTable();
    }

    @FXML
    public void viewUsers() throws IOException {
        FXMLLoader p = bladeLoad("viewUsers");
        UserController userController = p.getController();
        userController.setupTable();
    }
    
    @FXML
    public void viewOrders() throws IOException {
        FXMLLoader p = bladeLoad("viewOrdersAdmin");
        OrderController orderController = p.getController();
        orderController.setupTable();
    }
    
    public void signout(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/login.fxml"));
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    private FXMLLoader bladeLoad(String bladeName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/client/views/"+bladeName+".fxml"));
        Parent root = loader.load();
        borderpane.setCenter(root);
        return loader;

    }
    private void makeDragable(){
        borderpane.setOnMousePressed(((event)->{
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        borderpane.setOnMouseDragged(((event)->{
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);

        }));
    }
}
