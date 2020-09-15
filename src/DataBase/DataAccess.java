package DataBase;

import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataAccess {

    Connection connection;
    Statement statement;


    public DataAccess() {
        connection = DataBaseConnection.createDataBaseConnection();

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addNewUser(String userName, String password) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));
        parameters.add(addStringBetweenSingleQuote(password));

        statement.executeQuery("CALL add_new_user"
                + convertStringToParameters(parameters)
                + ";"
        );

    }

    public void addNewTask(int userID, String title, String dateTime, TaskStatus status, TaskPriority priority) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(title));
        parameters.add(addStringBetweenSingleQuote(dateTime));
        parameters.add(addStringBetweenSingleQuote(status.toString()));
        parameters.add(addStringBetweenSingleQuote(priority.toString()));

        statement.executeQuery("CALL add_new_task"
                + convertStringToParameters(parameters)
                + ";"
        );

    }

    public void addNewNote(int taskID, String note) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(note));

        statement.executeQuery(
                "CALL add_new_note"
                + convertStringToParameters(parameters)
                + ";"
        );

    }

    public void addNewTag(int taskID, String tag) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(tag));

        statement.executeQuery("CALL add_new_tag"
                + convertStringToParameters(parameters)
                + ";"
        );

    }

    public void deleteUser(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        statement.executeQuery(
                    "CALL delete_user"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void deleteTask(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        statement.executeQuery(
                "CALL delete_task"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void deleteNote(int taskID, String note) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(note));

        statement.executeQuery(
                "CALL delete_note"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void deleteTag(int taskID, String tag) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(tag));

        statement.executeQuery(
                "CALL delete_tag"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public int getLastInsertedID() throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT last_insert_id();");

        if (resultSet.next())
            return resultSet.getInt(1);

        return -1;

    }

    private String addStringBetweenSingleQuote(String string) {
        return "'" + string + "'";
    }

    private String convertStringToParameters(ArrayList<String> strings) {

        StringBuilder parameters = new StringBuilder();

        parameters.append('(');

        for (int i = 0; i < strings.size(); i++) {
            parameters.append(strings.get(i));
            if (i != strings.size() - 1)
                parameters.append(',');

        }

        parameters.append(')');

        return parameters.toString();

    }

}
