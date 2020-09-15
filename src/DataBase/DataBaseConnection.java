package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseConnection {

    private static Connection connection = null;

    public static Connection createDataBaseConnection () throws SQLException {

        if (connection == null) {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/todolistdb",
                    "todoListApp",
                    "pass"
            );

        }

        return connection;

    }

}
