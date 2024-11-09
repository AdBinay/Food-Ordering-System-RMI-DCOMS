package app.server;

import app.server.services.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

//import app.server.services.BusTimeService;

public class Server {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("\n\n ------------------ [ Server is running on port 1099 ::: Ready to receive requests! ] ------------------ ");

            LoginService login = new LoginService();
            registry.rebind("login", login);

            RegisterService register = new RegisterService();
            registry.rebind("register", register);

            UserService user = new UserService();
            registry.rebind("user", user);

            FoodService food = new FoodService();
            registry.rebind("food", food);
            
            OrderService order = new OrderService();
            registry.rebind("order", order);
            
            OrderItemService orderItem=new OrderItemService();
            registry.rebind("orderItem", orderItem);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
