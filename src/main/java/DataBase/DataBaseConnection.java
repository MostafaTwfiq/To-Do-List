package DataBase;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.util.Duration;

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

            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://0.0.0.0:1212/" + dataBaseName,
                        dataBaseUserName,
                        dataBaseUserPass
                );
            } catch (SQLException e) {
                TrayNotification trayNotification = new TrayNotification();
                trayNotification.setAnimation(Animations.FADE);
                trayNotification.setNotification(Notifications.ERROR);
                trayNotification.setTitle("Database error");
                trayNotification.setMessage("Can't access the database");
                trayNotification.showAndDismiss(Duration.seconds(2));
            }

        }

        return connection;

    }

}
