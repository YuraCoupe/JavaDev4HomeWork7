package ua.goit.projectmanagementsystem.config;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseManager {
    Connection getConnection() throws SQLException;
}
