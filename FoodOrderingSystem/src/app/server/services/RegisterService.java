package app.server.services;

import app.client.interfaces.RegisterInterface;
import app.server.database.DatabaseConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterService extends UnicastRemoteObject implements RegisterInterface {

    public RegisterService() throws RemoteException {}

    private boolean validRegister = false;

    @Override
    public boolean handleRegister(String name, String username,String email, String password) throws SQLException {

        Connection connection = DatabaseConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        if(!(name.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty())) {

            ResultSet resultSet = statement.executeQuery("select * from users where username='"+username+"'");

            if(resultSet.next()) {
                System.out.println("\n\n ### -->  Username has already been taken, please try a different Username!");
                validRegister = false;
            }
            else {
                statement.executeUpdate("insert into users(username,u_password,email,name,role) " +
                        "values('"+username+"','"+password+"','"+email+"','"+name+"', 1)");

                System.out.println("\n\n ### -->  User Registered Successfully! .... User is Authenticated" +
                        " [ *** Student Registry -> User is now logged in *** ] !");
                validRegister = true;
            }
        }
        else {
            System.out.println("\n\n ### --> All fields are required! Cannot have any of them blank!");
            validRegister = false;
        }

        return validRegister;
    }
}
