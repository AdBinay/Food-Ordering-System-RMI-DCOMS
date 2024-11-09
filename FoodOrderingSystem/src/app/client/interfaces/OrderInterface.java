package app.client.interfaces;

import app.models.Food;
import  app.models.Order;

import java.rmi.Remote;
import java.util.List;

public interface OrderInterface extends Remote {
    void addOrder(int custermer_id, String address,List<Food> food_list) throws Exception;
    List<Order> allOders() throws  Exception;
    List<Order> allMyOders(Integer oID) throws  Exception;
    void deleteOrder(Integer oID) throws Exception;
}
