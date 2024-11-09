package app.client.controllers;

import app.client.interfaces.OrderItemInterface;
import app.client.interfaces.LoginInterface;
import app.client.interfaces.OrderInterface;
import app.models.OrderItem;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class OrderItemController implements Initializable {
	Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
	OrderItemInterface orderItemInterface = (OrderItemInterface) registry.lookup("orderItem");
	OrderInterface orderInterface = (OrderInterface) registry.lookup("order");
	LoginInterface loginInterface = (LoginInterface) registry.lookup("login");
	ObservableList<OrderItem> list = FXCollections.observableArrayList();

	public OrderItemController() throws RemoteException, NotBoundException {
	}

	private Order order;
	private Double cal_total;
	@FXML
	private TableView<OrderItem> table;

	@FXML
	private TableView<Food> table2;

	@FXML
	private TableColumn<OrderItem, Integer> col_fId;

	@FXML
	private TableColumn<OrderItem, String> col_food;

	@FXML
	private TableColumn<OrderItem, String> col_price;

	@FXML
	private TableColumn<OrderItem, Integer> col_quantity;

	@FXML
	private TableColumn<Food, String> col_txt_quantity;

	@FXML
	private TextField cId;

	@FXML
	private TextField oId;

	@FXML
	private TextField timestamp;

	@FXML
	private TextField total;

	@FXML
	private TextField address;

	public void setData(Order order) {
		try {
			list.addAll(orderItemInterface.orderItems(order.getOrder_id()));

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.order = order;
		col_fId.setCellValueFactory(new PropertyValueFactory<>("fid"));
		col_food.setCellValueFactory(new PropertyValueFactory<>("food"));
		col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		table.setItems(list);
		cId.setText(String.valueOf(order.getCustomer_id()));
		oId.setText(String.valueOf(order.getOrder_id()));
		timestamp.setText(order.getTimestamp());
		total.setText(String.valueOf(order.getTotal()));
	}

	@FXML
	public void back(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/admin.fxml"));
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setScene(new Scene(root));
	}

	@FXML
	public void delivered(MouseEvent event) throws IOException {
		try {
			orderItemInterface.handleDelivered(this.order.getOrder_id());
			Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/admin.fxml"));
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private static Double getTotal(TableView tableView) {
		ObservableList<TableColumn> columns = tableView.getColumns();
		Double total = 0.0;
		for (Object row : tableView.getItems()) {
			int c = 0;
			int price = 0;
			int quantity = 0;
			for (TableColumn column : columns) {
				if (c == 2) {
					price = Integer.parseInt((String) column.getCellObservableValue(row).getValue());
				}
				if (c == 3) {
					quantity = Integer.parseInt((String) column.getCellObservableValue(row).getValue());
				}
				c = c + 1;
			}
			total = total + price * quantity;
		}

		return total;
	}

	@FXML
	public void calTotal(MouseEvent event) throws IOException {
		cal_total = getTotal(table2);
		total.setText(String.valueOf(cal_total));
	}

	@FXML
	public void backUser(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/app/client/views/Customer.fxml"));
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.setScene(new Scene(root));
	}

	@FXML
	public void order(MouseEvent event) throws IOException {

		List<Food> food_list = new ArrayList<Food>();
		ObservableList<Food> items = table2.getItems();

		for (Food f : items) {
			System.out.println(f.getFname());
			food_list.add(f);
		}

		try {
			orderInterface.addOrder(loginInterface.getUserID(), address.getText(), food_list);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setData(ObservableList<Food> lst) throws IOException {
		table2.setEditable(true);
		try {
			col_fId.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_food.setCellValueFactory(new PropertyValueFactory<>("fname"));
			col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			col_txt_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			col_txt_quantity.setCellFactory(TextFieldTableCell.forTableColumn());

			col_txt_quantity.setOnEditCommit((TableColumn.CellEditEvent<Food, String> t) -> (t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue()));
			table2.setItems(lst);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setMyData(Order order) {
		try {
			list.addAll(orderItemInterface.orderItems(order.getOrder_id()));

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.order = order;
		col_fId.setCellValueFactory(new PropertyValueFactory<>("fid"));
		col_food.setCellValueFactory(new PropertyValueFactory<>("food"));
		col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		table.setItems(list);
		oId.setText(String.valueOf(order.getOrder_id()));
		timestamp.setText(order.getTimestamp());
		total.setText(String.valueOf(order.getTotal()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
