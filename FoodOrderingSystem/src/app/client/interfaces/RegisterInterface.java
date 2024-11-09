package app.client.interfaces;

import java.io.IOException;
import java.rmi.Remote;
import java.sql.SQLException;

public interface RegisterInterface extends Remote {
    boolean handleRegister(String name, String username,String email, String password) throws IOException,SQLException;
}
