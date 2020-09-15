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


    public DataAccess() throws SQLException {

        connection = DataBaseConnection.createDataBaseConnection();

        statement = connection.createStatement();

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

    public void updateUserName(int userID, String username) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(username));

        statement.executeQuery(
                "CALL update_user_name"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateUserPassword(int userID, String password) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(password));

        statement.executeQuery(
                "CALL update_user_password"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskTitle(int taskID, String title) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(title));

        statement.executeQuery(
                "CALL update_task_title"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskDateTime(int taskID, String datetime) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(datetime));

        statement.executeQuery(
                "CALL update_task_datetime"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskStatus(int taskID, TaskStatus status) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(status.toString()));

        statement.executeQuery(
                "CALL update_task_status"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskPriority(int taskID, TaskPriority priority) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(priority.toString()));

        statement.executeQuery(
                "CALL update_task_priority"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskNote(int taskID, String oldNote, String newNote) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(oldNote));
        parameters.add(addStringBetweenSingleQuote(newNote));

        statement.executeQuery(
                "CALL update_note"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }

    public void updateTaskTag(int taskID, String oldTag, String newTag) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(oldTag));
        parameters.add(addStringBetweenSingleQuote(newTag));

        statement.executeQuery(
                "CALL update_tag"
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
