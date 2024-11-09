package app.client.controllers;
import javafx.collections.ObservableList;
import app.client.interfaces.FoodInterface;
import app.client.interfaces.OrderInterface;
import app.models.Food;
import app.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import static java.lang.System.out;

import java.io.IOException;

import javafx.scene.control.TablePosition;
public class FoodController implements Initializable {
    Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
    FoodInterface foodInterface =(FoodInterface) registry.lookup("food");
    ObservableList<Food> list = FXCollections.observableArrayList();
    ObservableList<Food> selectedCells = FXCollections.observableArrayList();
    boolean validFood = false;

    @FXML
    String fname, price,category;



    public FoodController() throws RemoteException, NotBoundException {
    }

    @FXML
    private TextField col_food_name;

    @FXML
    private TextField col_food_price;
    
    @FXML
    private TextField col_food_category;

    @FXML
    private TableView<Food> table;
    @FXML
    private TableColumn<Food, Integer> col_id;

    @FXML
    private TableColumn<Food,String> col_fname;

    @FXML
    private TableColumn<Food, String> col_price;
    
    @FXML
    private TableColumn<Food, String> col_category;

    public void addFood() {

        fname = col_food_name.getText();
        price = col_food_price.getText();
        category = col_food_category.getText();
        try{


            validFood = foodInterface.addFood(fname, price,category);

            if(validFood == true) {

                out.println("\n\n-- [ Food is added successfully! ] --");
            }
            else {
                out.println("\n\n-- [ Cannot add food : Operation Failed! ] --");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setupTable(){
        try {
            list.addAll(foodInterface.allFood());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        col_category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        col_fname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("Id"));

        table.setItems(list);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }
    @FXML
    public void order(MouseEvent mouseEvent) throws IOException {    
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/client/views/myOrder.fxml"));
    	Parent root = loader.load();
    	Node node = (Node) mouseEvent.getSource();
       Stage stage = (Stage)node.getScene().getWindow();
       stage.setScene(new Scene(root));
       selectedCells=table.getSelectionModel().getSelectedItems();
       OrderItemController orderItemController = loader.getController();
       orderItemController.setData(selectedCells);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
