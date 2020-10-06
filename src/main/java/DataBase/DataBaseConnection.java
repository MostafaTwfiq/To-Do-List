package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseConnection {

    private static Connection connection = null;

    private static final String dataBaseName = "todoListDB";
    private static final String dataBaseUserName = "todoListApp";
    private static final String dataBaseUserPass = "pass";

    public static Connection createDataBaseConnection () throws SQLException {

        if (connection == null) {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://0.0.0.0:1212/" + dataBaseName,
                    dataBaseUserName,
                    dataBaseUserPass
            );

        }

        return connection;

    }

}
