package app.server.services;

import app.client.interfaces.FoodInterface;
import app.models.Food;
import app.models.User;
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

public class FoodService extends UnicastRemoteObject implements FoodInterface {
    boolean valid = false;

    Connection connection = DatabaseConnection.getInstance().getConnection();
    Statement statement = connection.createStatement();


    public FoodService() throws RemoteException, SQLException {
    }

    @Override
    public boolean addFood(String food, String price, String category) throws IOException, SQLException {

        if(!(food.isEmpty()) && !(price.isEmpty()) && !(category.isEmpty())) {

            statement.executeUpdate("insert into food(fname,price,category) " +
                    "values('"+food+"','"+price+"','"+category+"')");

            System.out.println("\n\n ###------------------------ Food Added Successfully! -------------------------");
            valid = true;
        }
        else {
            System.out.println("\n\n ### --> All fields are required!");
            valid = false;
        }

        return valid;
    }

    @Override
    public List<Food> allFood() throws RemoteException {
        try{
            ResultSet resultSet = statement.executeQuery("select * from food");

            List<Food> list = new ArrayList<Food>();

            while(resultSet.next()) {
                Food food = new Food();
                food.setId(resultSet.getInt("id"));
                food.setFname(resultSet.getString("fname"));
                food.setPrice(resultSet.getString("price"));
                food.setCategory(resultSet.getString("category"));
                list.add(food);
            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

}
