package app.server.services;

import app.client.interfaces.OrderItemInterface;
import app.models.OrderItem;
import app.server.database.DatabaseConnection;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderItemService extends UnicastRemoteObject implements OrderItemInterface {
    boolean valid = false;

    Connection connection = DatabaseConnection.getInstance().getConnection();
    Statement statement = connection.createStatement();

    public OrderItemService() throws RemoteException, SQLException {
    }    
    
    @Override
    public void handleDelivered(Integer oID) throws SQLException{
    	Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("update orders set status='completed' where id="+String.valueOf(oID));
        System.out.println("Order Updated");
    }
    
    @Override
    public List<OrderItem> orderItems(Integer oID) throws RemoteException {
      
    	try{
            ResultSet resultSet = statement.executeQuery("select oi.food_id as fid,oi.quantity ,f.fname as food,oi.order_id as oid,o.customer_id as cid,o.timestamp, f.price from order_items oi left outer join orders o on oi.order_id=o.id left outer join food f on f.id=oi.food_id where oi.order_id="+String.valueOf(oID));
            
            List<OrderItem> list = new ArrayList<OrderItem>();
            while(resultSet.next()) {
            	OrderItem orderItem = new OrderItem();
            	
            	orderItem.setFid(resultSet.getInt("fid"));
            	orderItem.setFood(resultSet.getString("food"));
            	orderItem.setOid(resultSet.getInt("oid"));
            	orderItem.setCid(resultSet.getInt("cid"));
                orderItem.setTimestamp(resultSet.getString("timestamp"));
                orderItem.setPrice(resultSet.getString("price"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                list.add(orderItem);
            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
