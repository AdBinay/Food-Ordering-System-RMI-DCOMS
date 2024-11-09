package app.client.interfaces;

import java.io.IOException;
import java.rmi.*;
import java.sql.SQLException;

// The login interface extending the Remote interface
public interface LoginInterface extends Remote {
	
	Integer getUserID() throws RemoteException;
    // The login method that is going to be invoked by the remote client
    Integer obtainLogin(String username, String password) throws IOException, SQLException;
}
