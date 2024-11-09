package app.server.services;

import app.client.interfaces.OrderInterface;
import app.models.Food;
import app.models.Order;
import app.server.database.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderService extends UnicastRemoteObject implements OrderInterface {
    boolean valid = false;

    

    public OrderService() throws RemoteException {}

     

    @Override
    public List<Order> allOders() throws Exception {
      
        	Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select timestamp ,order_id,customer_id,total,address,status from (select order_id, sum(f.price*o.quantity) as total from order_items o left outer join food f on o.food_id=f.id group by o.order_id ) t1 left outer join orders os on os.id=t1.order_id order by timestamp desc");
            
            List<Order> list = new ArrayList<Order>();
            while(resultSet.next()) {
                Order order = new Order();
                order.setCustomer_id(resultSet.getInt("customer_id"));
                order.setOrder_id(resultSet.getInt("order_id"));
                order.setAddress(resultSet.getString("address"));
                order.setTimestamp(resultSet.getString("timestamp"));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getString("status"));
                list.add(order);
            }
            return list;

        
    }
    
    @Override
    public List<Order> allMyOders(Integer oID) throws Exception {
      
        	Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select timestamp ,customer_id,order_id,total,address,status from (select order_id, sum(f.price*o.quantity) as total from order_items o left outer join food f on o.food_id=f.id group by o.order_id ) t1 left outer join orders os on os.id=t1.order_id where os.customer_id="+String.valueOf(oID)+" order by timestamp desc " );
      
            List<Order> list = new ArrayList<Order>();
            while(resultSet.next()) {
                Order order = new Order();
                order.setCustomer_id(resultSet.getInt("customer_id"));
                order.setOrder_id(resultSet.getInt("order_id"));
                order.setAddress(resultSet.getString("address"));
                order.setTimestamp(resultSet.getString("timestamp"));
                order.setTotal(resultSet.getDouble("total"));
                order.setStatus(resultSet.getString("status"));
                list.add(order);
            }
            return list;

        
    }



	@Override
	public void addOrder(int custermer_id, String address, List<Food> food_list) throws  Exception {
		Connection connection = DatabaseConnection.getInstance().getConnection();
	    Statement statement = connection.createStatement();
	            statement.executeUpdate("insert into orders(customer_id ,address) " +
	                    "values('"+custermer_id+"','"+address+"')");
	            Thread.sleep(500);
	            ResultSet resultSet=statement.executeQuery("select max(id) as max_id from orders");
	            int order_id=0;
	            while(resultSet.next()) {
	            	order_id=resultSet.getInt("max_id");
	            }
	           
	            for(Food f: food_list) {
	            	statement.executeUpdate("insert into order_items(order_id ,food_id ,quantity ) " +
		                    "values('"+order_id+"','"+f.getId()+"','"+f.getQuantity()+"')");
	            }
//	            
	            
	            
	            System.out.println("\n\n ###------------------------ Order Placed Successfully! -------------------------");
		
	       
		
	}



	@Override
	public void deleteOrder(Integer oID) throws Exception {
		Connection connection = DatabaseConnection.getInstance().getConnection();
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("delete from orders where id = "+oID);
	    statement.executeUpdate("delete from order_items where order_id = "+oID);
		
	    System.out.println("\n\n ###------------------------ Order Deleted Successfully! -------------------------");
		
	}
}
