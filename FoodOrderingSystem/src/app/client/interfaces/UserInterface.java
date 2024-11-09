package app.client.interfaces;

import app.models.User;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface UserInterface extends Remote {
    boolean registerAdmin(String name, String email,String username, String password) throws IOException,SQLException;

    boolean registerCustomer(String name, String email,String username, String password) throws IOException,SQLException;

    boolean changePassword(String username, String oldPassword, String newPassword, String repeatPassword) throws IOException, SQLException;

    List<User> allUsers() throws RemoteException;
}
