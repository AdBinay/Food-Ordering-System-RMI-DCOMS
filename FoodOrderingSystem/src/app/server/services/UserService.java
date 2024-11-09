package app.server.services;

import app.client.interfaces.UserInterface;
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

public class UserService extends UnicastRemoteObject implements UserInterface {
    boolean valid = false;

    Connection connection = DatabaseConnection.getInstance().getConnection();
    Statement statement = connection.createStatement();

    public UserService() throws RemoteException, SQLException {
    }

    @Override
    public boolean registerAdmin(String name, String email, String username, String password) throws IOException, SQLException {

        if(!(name.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty()) && !(email.isEmpty())) {

            ResultSet resultSet = statement.executeQuery("select * from users where username='"+username+"'");

            if(resultSet.next()) {
                System.out.println("\n\n ### -->  Username has already been taken, please use a different Username to create this user!");
                valid = false;
            }
            else {
                statement.executeUpdate("insert into users(username,u_password,name,role,email) " +
                        "values('"+username+"','"+password+"','"+name+"', 0,'"+email+"')");

                System.out.println("\n\n ### -->  User Registered Successfully! ...." +
                        " [ *** Admin has been created -> User can login to the system at anytime *** ] !");
                valid = true;
            }
        }
        else {
            System.out.println("\n\n ### --> All fields are required! Cannot have any of them blank!");
            valid = false;
        }

        return valid;
    }


    @Override
    public boolean registerCustomer(String name, String email, String username, String password) throws IOException, SQLException {

        if(!(name.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty()) && !(email.isEmpty())) {

            ResultSet resultSet = statement.executeQuery("select * from users where username='"+username+"'");

            if(resultSet.next()) {
                System.out.println("\n\n ### -->  Username has already been taken, please use a different Username to create this user!");
                valid = false;
            }
            else {
                statement.executeUpdate("insert into users(username,u_password,name,role,email) " +
                        "values('"+username+"','"+password+"','"+name+"', 1,'"+email+"')");

                System.out.println("\n\n ### -->  User Registered Successfully! ...." +
                        " [ *** Admin created -> User can login to the system at anytime *** ] !");
                valid = true;
            }
        }
        else {
            System.out.println("\n\n ### --> All fields are required! Cannot have any of them blank!");
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword, String repeatPassword) throws IOException, SQLException {
        boolean validOperation = false;

        if(!(username.isEmpty() && !(oldPassword.isEmpty()) && !(newPassword.isEmpty()) && !(repeatPassword.isEmpty()))) {

            if(newPassword.equals(repeatPassword)) {
                ResultSet resultSet = statement.executeQuery("select * from users where username='"+username+"' and u_password = '"+oldPassword+"'");

                if(resultSet.next()) {
                    statement.executeUpdate("update users set u_password = '"+newPassword+"' where username = '"+username+"'");

                    System.out.println("\n\n [-- Password has already been changed! --]");
                    validOperation = true;
                }
                else {
                    System.out.println("\n\nInvalid user, make sure you insert the right details");
                    validOperation = false;
                }
            }
            else {
                System.out.println("\n\n [[-- Repeated new password should match the new password! --]]");
                validOperation = false;
            }
        }
        else {
            System.out.println("\n\n [[-- All Fields are required, You cannot skip any of them --]] ###");
            validOperation = false;
        }
        return validOperation;
    }

    @Override
    public List<User> allUsers() throws RemoteException {
        try{
            ResultSet resultSet = statement.executeQuery("select * from users");

            List<User> list = new ArrayList<User>();

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("u_password"));
                user.setRole(resultSet.getInt("role"));
                list.add(user);
            }
            return list;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
}
