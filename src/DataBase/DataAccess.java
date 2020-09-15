package DataBase;

import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataAccess {

    Connection connection;
    Statement statement;


    public DataAccess() throws SQLException {

        connection = DataBaseConnection.createDataBaseConnection();

        statement = connection.createStatement();

    }

    public int getUserID(String userName, String password) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));
        parameters.add(addStringBetweenSingleQuote(password));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_id"
                + convertStringToParameters(parameters)
                + ";"
        );

        if (resultSet.next())
            return resultSet.getInt(1);

        return -1;

    }

    public int getNumOfTasks(int userID, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(date));

        ResultSet resultSet = statement.executeQuery(
                "SELECT get_user_tasks_num_at_date"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getInt(1);

        return -1;

    }

    public List<Integer> getTasksIDs(int userID, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(date));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_tasks_ids_by_user_id_date"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        ArrayList<Integer> ids = new ArrayList<>();

        while (resultSet.next())
            ids.add(resultSet.getInt(1));

        return ids;

    }

    public List<Integer> getTasksIDs(int userID, List<String> tags, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(convertTagsArrToParam(tags)));
        parameters.add(addStringBetweenSingleQuote(date));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_tasks_ids_by_user_id_tags_date"
                + convertStringToParameters(parameters)
                + ";"
        );

        ArrayList<Integer> ids = new ArrayList<>();

        while (resultSet.next())
            ids.add(resultSet.getInt(1));

        return ids;

    }

    public List<Integer> getTasksIDs(int userID, String subTitle, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(subTitle));
        parameters.add(addStringBetweenSingleQuote(date));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_tasks_ids_by_user_id_title_rgx_date"
                + convertStringToParameters(parameters)
                + ";"
        );

        ArrayList<Integer> ids = new ArrayList<>();

        while (resultSet.next())
            ids.add(resultSet.getInt(1));

        return ids;

    }

    public List<Integer> getTasksIDs(int userID, String subTitle, List<String> tags, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(subTitle));
        parameters.add(addStringBetweenSingleQuote(convertTagsArrToParam(tags)));
        parameters.add(addStringBetweenSingleQuote(date));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_tasks_ids_by_user_id_title_rgx_tags_date"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        ArrayList<Integer> ids = new ArrayList<>();

        while (resultSet.next())
            ids.add(resultSet.getInt(1));

        return ids;

    }


    public Task getTaskFullInfo(int taskID) throws SQLException {

        Task task = getTaskInfo(taskID);

        if (task == null)
            return null;

        task.setNotes(getTaskNotes(taskID));
        task.setTags(getTaskTags(taskID));

        return task;

    }

    public List<Task> getTaskFullInfo(List<Integer> taskIDs) throws SQLException {

        List<Task> tasks = new ArrayList<>();

        for (int id : taskIDs) {
            Task task = getTaskInfo(id);

            if (task == null)
                continue;

            task.setNotes(getTaskNotes(id));
            task.setTags(getTaskTags(id));

            tasks.add(task);

        }

        return tasks;

    }

    public Task getTaskInfo(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_task_info_by_task_id"
                + convertStringToParameters(parameters)
                + ";"
        );

        Task task = null;

        if (resultSet.next()) {

            task = new Task(
                    taskID,
                    resultSet.getString(1),
                    resultSet.getString(2),
                    TaskStatus.valueOf(resultSet.getString(3)),
                    TaskPriority.valueOf(resultSet.getString(4))
            );

        }

        return task;

    }

    public List<Task> getTaskInfo(List<Integer> taskIDs) throws SQLException {

        List<Task> tasks = new ArrayList<>();

        for (int id : taskIDs) {
            Task task = getTaskInfo(id);

            if (task == null)
                continue;

            tasks.add(task);

        }

        return tasks;

    }

    public List<String> getTaskNotes(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_task_notes"
                + convertStringToParameters(parameters)
                + ";"
        );

        ArrayList<String> notes = new ArrayList<>();

        while (resultSet.next())
            notes.add(resultSet.getString(1));

        return notes;

    }

    public List<String> getTaskTags(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_task_tags"
                + convertStringToParameters(parameters)
                + ";"
        );

        ArrayList<String> tags = new ArrayList<>();

        while (resultSet.next())
            tags.add(resultSet.getString(1));

        return tags;

    }

    public void addNewUser(String userName, String password) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));
        parameters.add(addStringBetweenSingleQuote(password));

        statement.executeQuery(
                "CALL add_new_user"
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

        statement.executeQuery(
                "CALL add_new_task"
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

        statement.executeQuery(
                "CALL add_new_tag"
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

    private String convertTagsArrToParam(List<String> tags) {

        StringBuilder tagsParma = new StringBuilder();

        for (int i = 0; i < tags.size(); i++) {
            tagsParma.append(tags.get(i));

            if (i != tags.size() - 1)
                tagsParma.append('|');

        }

        return tagsParma.toString();

    }

}
