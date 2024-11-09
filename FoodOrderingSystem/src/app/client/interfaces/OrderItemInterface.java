package app.client.interfaces;

import  app.models.OrderItem;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface OrderItemInterface extends Remote {
	void handleDelivered(Integer oID) throws IOException,SQLException;
    List<OrderItem> orderItems(Integer oID) throws RemoteException;
}
