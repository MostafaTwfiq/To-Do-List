package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DataBaseConnection {

    private static Connection connection = null;

    public static Connection createDataBaseConnection () {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolistdb", "todoListApp", "pass");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return connection;

    }

}
