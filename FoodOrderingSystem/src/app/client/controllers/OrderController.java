package app.client.controllers;
import app.client.interfaces.LoginInterface;
import app.client.interfaces.OrderInterface;
import app.models.Order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class OrderController implements Initializable {
	Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
	OrderInterface orderInterface =(OrderInterface) registry.lookup("order");
	LoginInterface loginInterface = (LoginInterface) registry.lookup("login");
    
    ObservableList<Order> list = FXCollections.observableArrayList();
    
    boolean valid = false;

    @FXML
    String date_time,address;

   
    public OrderController() throws RemoteException, NotBoundException {
    }
    


    @FXML
    private TableView<Order> table;

	@FXML
    private TableColumn<Order,String> col_timestamp;

    @FXML
    private TableColumn<Order, Integer> col_order_id;

    @FXML
    private TableColumn<Order, Integer> col_customer_id;
    
    @FXML
    private TableColumn<Order, Double> col_total;
    
    @FXML
    private TableColumn<Order, String> col_address;

    @FXML
    private TableColumn<Order, String> col_status;

 
    public void setupTable(){
        try {
            list.addAll(orderInterface.allOders());

        } catch (Exception e) {
            e.printStackTrace();
        }
        col_timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        col_order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        col_customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);
        
    }
    
    public void setupTable2(){
    	
        try {
            list.addAll(orderInterface.allOders());

        } catch (Exception e) {
            e.printStackTrace();
        }
        col_timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        col_order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        col_customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);
        
    }
    
    public void setupTable3(){
        try {
            list.addAll(orderInterface.allMyOders(loginInterface.getUserID()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        col_timestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        col_order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);
        
    }
    @FXML
    public void viewOrder(MouseEvent mouseEvent) throws IOException {       
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/client/views/viewOrderAdmin.fxml"));
    	Parent root = loader.load();
    	Node node = (Node) mouseEvent.getSource();
       Stage stage = (Stage)node.getScene().getWindow();
       stage.setScene(new Scene(root));
       Order order = table.getSelectionModel().getSelectedItem();
       OrderItemController orderItemController = loader.getController();
       orderItemController.setData(order);
    }
    
    @FXML
    public void viewOrderCustomer(MouseEvent mouseEvent) throws IOException {       
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/client/views/viewOrderCustomer.fxml"));
    	Parent root = loader.load();
    	Node node = (Node) mouseEvent.getSource();
       Stage stage = (Stage)node.getScene().getWindow();
       stage.setScene(new Scene(root));
       Order order = table.getSelectionModel().getSelectedItem();
       OrderItemController orderItemController = loader.getController();
       orderItemController.setMyData(order);
    }
    
    @FXML
    public void deleteOrderCustomer(MouseEvent mouseEvent) throws Exception {       
    	Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/Customer.fxml"));
		Node node = (Node) mouseEvent.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setScene(new Scene(root));
		Order order = table.getSelectionModel().getSelectedItem();
		
		orderInterface.deleteOrder(order.getOrder_id());
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
      
    }
