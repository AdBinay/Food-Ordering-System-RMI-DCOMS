package app.client.interfaces;
import  app.models.Food;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface FoodInterface extends Remote {
    boolean addFood(String fname, String price, String category) throws IOException,SQLException;

    List<Food> allFood() throws RemoteException;
}
