package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseConnection {

    private static Connection connection = null;

    private static final String dataBaseName = "todolistdb";
    private static final String dataBaseUserName = "todoListApp";
    private static final String dataBaseUserPass = "pass";

    public static Connection createDataBaseConnection () throws SQLException {

        if (connection == null) {

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dataBaseName,
                    dataBaseUserName,
                    dataBaseUserPass
            );

        }

        return connection;

    }

}
