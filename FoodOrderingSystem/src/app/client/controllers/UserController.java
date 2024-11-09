package app.client.controllers;

import app.client.interfaces.UserInterface;
import app.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class UserController implements Initializable {

    ObservableList<User> list = FXCollections.observableArrayList();

    boolean valid = false;

    @FXML
    String name, username, password, email;

    Registry registry = LocateRegistry.getRegistry("192.168.1.69", 1099);
    UserInterface userInterface =(UserInterface) registry.lookup("user");

    public UserController() throws RemoteException, NotBoundException {
    }

    @FXML
    private TextField s_name;

    @FXML
    private TextField s_email;

    @FXML
    private TextField s_username;

    @FXML
    private TextField s_password;

    @FXML
    private TextField a_name;

    @FXML
    private TextField a_email;

    @FXML
    private TextField a_username;

    @FXML
    private TextField a_password;

    @FXML
    private TextField col_u_name;

    @FXML
    private TextField col_old;

    @FXML
    private TextField col_new;

    @FXML
    private TextField col_new_repeat;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User,String> col_name;

    @FXML
    private TableColumn<User, String> col_username;

    @FXML
    private TableColumn<User, String> col_email;

    @FXML
    private TableColumn<User, Integer> col_id;

    public void registerAdmin() {

        name = a_name.getText();
        username = a_username.getText();
        password = a_password.getText();
        email = a_email.getText();

        try{
            valid = userInterface.registerAdmin(name,email,username, password);

            if(valid == true) {
                out.println("\n\n-- [ Registered Successfully --> User has been registered: Operation is successful! ] --");
            }
            else {
                out.println("\n\n-- [ Failed Registry --> Cannot register user : Operation Failed! ] --");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void registerCustomer() {

        name = s_name.getText();
        username = s_username.getText();
        password = s_password.getText();
        email = s_email.getText();

        try{
            valid = userInterface.registerCustomer(name,email,username, password);

            if(valid == true) {
                out.println("\n\n-- [ Registered Successfully --> User has been registered: Operation is successful! ] --");
            }
            else {
                out.println("\n\n-- [ Failed Registry --> Cannot register user : Operation Failed! ] --");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changePassword(MouseEvent mouseEvent) {

        boolean validOperation = false;

        String username, oldPass, newPass, newPassRepeat;
        username = col_u_name.getText();
        oldPass = col_old.getText();
        newPass = col_new.getText();
        newPassRepeat = col_new_repeat.getText();

        try {
            validOperation = userInterface.changePassword(username,oldPass,newPass,newPassRepeat);

            if(validOperation == true) {
                System.out.println("\n\n-- [ User password has already been changed! ] --");
            }
            else {
                System.out.println("\n\n-- [ Oops There was an issue of the provided details, please make sure everything was right ] --");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }




    public void setupTable(){
        try {
            list.addAll(userInterface.allUsers());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        col_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        table.setItems(list);
    }



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
