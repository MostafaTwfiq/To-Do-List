package DataBase;

import DataClasses.Task;
import DataClasses.TaskStatus.TaskPriority;
import DataClasses.TaskStatus.TaskStatus;
import GUI.Style.Style.Theme;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccess {

    Connection connection;
    Statement statement;


    public DataAccess() throws SQLException {

        connection = DataBaseConnection.createDataBaseConnection();

        statement = connection.createStatement();

    }

    /** This function will return the user id with the provided user name and password.
     * @param userName user name
     * @param password user password
     * @return it will return the user id
     * @throws SQLException exception in case some thing went wrong while reading data
     */

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


    /** This function will return the user id with the provided user name.
     * @param userName user name
     * @return it will return the user id
     * @throws SQLException exception in case some thing went wrong while reading data
     */

    public int getUserID(String userName) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_id_by_user_name"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getInt(1);

        return -1;

    }


    /** This function will return the user name for the provided id.
     * @param userID the user id
     * @return it will return the user name for the provided id, and it will return null if the id wasn't found
     * @throws SQLException exception in case something went wrong while reading the data
     */

    public String getUserName(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_name"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getString(1);

        return null;

    }


    /** This function will return the user password for the provided id.
     * @param userID the user id
     * @return it will return the user password for the provided id, and it will return null if the id wasn't found
     * @throws SQLException exception in case something went wrong while reading the data
     */

    public String getUserPassword(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_password"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getString(1);

        return null;

    }



    /** This function will return the theme for the user with the provided id.
     * @param userID the user id
     * @return it will return the theme of the user with the passed id.
     * @throws SQLException exception in case something went wrong while reading the data
     */

    public Theme getUserTheme(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "SELECT get_user_theme"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return Theme.valueOf(resultSet.getString(1));

        return null;

    }


    /** This function will return the image path for the passed user id if found, other wise it will return null.
     * @param userID the user id
     * @return it will return the user image path if found, other wise it will return null
     * @throws SQLException exception in case something went wrong
     */

    public String getUserImagePath(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "SELECT get_user_image_path"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getString(1);

        return null;

    }


    /** This function will return the number of tasks on the passed day for the passed user id.
     * @param userID user id
     * @param date day date YYYY-MM-dd
     * @return it will return the number of tasks on the passed day for a specific user
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return a list of tasks ids that is on the passed day for the passed user id.
     * @param userID user id
     * @param date the day date YYY-MM-dd
     * @return it will return the tasks ids on the passed date for specific user
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return the tasks ids from the passed user with the passed tags on the passed day.
     * @param userID user id
     * @param tags tags list
     * @param date day date YYYY-MM-dd
     * @return it will return the tasks ids from the passed user with the passed tags on the passed day
     * @throws SQLException exception in case something went wrong
     */

    public List<Integer> getTasksIDs(int userID, List<String> tags, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        if (!tags.isEmpty())
            parameters.add(addStringBetweenSingleQuote(convertListToRegexParam(tags)));
        else
            parameters.add("NULL");
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


    /** This function will return the tasks ids for the passed user id on the passed date with the sub title.
     * @param userID user id
     * @param subTitle task sub title
     * @param date day date YYYY-MM-dd
     * @return it will return the tasks ids for the passed user id on the passed date with the sub title
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return the tasks ids for the passed user id on the passed date with the passed tags and passed sub title.
     * @param userID the user id
     * @param subTitle the task sub title
     * @param tags the tags list
     * @param date the day date YYYY-MM-dd
     * @return it will return the tasks ids for the passed user id on the passed date with the passed tags and passed sub title
     * @throws SQLException exception in case something went wrong
     */

    public List<Integer> getTasksIDs(int userID, String subTitle, List<String> tags, String date) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(subTitle));
        if (!tags.isEmpty())
            parameters.add(addStringBetweenSingleQuote(convertListToRegexParam(tags)));
        else
            parameters.add("NULL");
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


    /** This function will return the tasks ids for the passed user id filtered by the passed info.
     * @param userID the user id
     * @param subTitle the task sub title
     * @param tags the tags list
     * @param statuses the statuses list that the function will filter the tasks with it
     * @param priorities the priorities list that the function will filter the tasks with it
     * @param date the day date YYYY-MM-dd
     * @param isStarred a boolean represents the statues of the task where is it starred or not
     * @return it will return the tasks ids for the passed user id filtered by the passed info
     * @throws SQLException exception in case something went wrong
     */

    public List<Integer> getTasksIDs(int userID,
                                     String subTitle,
                                     List<String> tags,
                                     List<String> statuses,
                                     List<String> priorities,
                                     String date,
                                     Boolean isStarred) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(subTitle));
        if (!tags.isEmpty())
            parameters.add(addStringBetweenSingleQuote(convertListToRegexParam(tags)));
        else
            parameters.add("NULL");
        if (!statuses.isEmpty())
            parameters.add(addStringBetweenSingleQuote(convertListToRegexParam(statuses)));
        else
            parameters.add("NULL");
        if (!priorities.isEmpty())
            parameters.add(addStringBetweenSingleQuote(convertListToRegexParam(priorities)));
        else
            parameters.add("NULL");
        parameters.add(addStringBetweenSingleQuote(date));
        if (isStarred != null)
            parameters.add(String.format("%b", isStarred));
        else
            parameters.add("NULL");

        ResultSet resultSet = statement.executeQuery(
                "CALL get_user_tasks_ids_full_search"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        ArrayList<Integer> ids = new ArrayList<>();

        while (resultSet.next())
            ids.add(resultSet.getInt(1));

        return ids;

    }


    /** This function will return a task object that contains the full info (including notes and tags) of the passed tag id.
     * Note: the function will return null if the passed task id didn't found.
     * @param taskID the task id
     * @return it will return a task object for the passed task id
     * @throws SQLException exception in case something went wrong
     */

    public Task getTaskFullInfo(int taskID) throws SQLException {

        Task task = getTaskInfo(taskID);

        if (task == null)
            return null;

        task.setNotes(getTaskNotes(taskID));
        task.setTags(getTaskTags(taskID));

        return task;

    }


    /** This function will return a task list that contains the full data (including notes and tags) of the passed tasks ids.
     * @param taskIDs the tasks ids list
     * @return it will return a tasks list with it's full info that contains all the passed tasks ids
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return a task object that contains the passed task id information (without notes and tags).
     * @param taskID the task id
     * @return it will return a task object that contains the passed task id information
     * @throws SQLException exception in case something went wrong
     */

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
                    TaskPriority.valueOf(resultSet.getString(4)),
                    resultSet.getBoolean(5)
            );

        }

        return task;

    }


    /** This function will return a tasks list that contains all the passed tasks ids list information (without notes and tags).
     * @param taskIDs the tasks ids list
     * @return it will return a tasks list that contains all the passed tasks ids list information
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return the notes for the passed task id.
     * @param taskID the task id
     * @return it will return the notes for the passed task id
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return the tags for the passed task id.
     * @param taskID the task id
     * @return it will return the tags associated with the passed task id
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will add a new user with the passed info.
     * @param userName the new user name
     * @param password the new user password
     * @param theme the favorite theme of the user
     * @throws SQLException exception in case something went wrong
     */

    public void addNewUser(String userName, String password, Theme theme) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));
        parameters.add(addStringBetweenSingleQuote(password));
        parameters.add(addStringBetweenSingleQuote(theme.toString()));

        statement.executeQuery(
                "CALL add_new_user"
                + convertStringToParameters(parameters)
                + ";"
        );

    }



    /** This function will add the passed image path in the images paths tables, for the passed user id.
      * @param userID the user id
     * @param path the image path
     * @throws SQLException exception in case something went wrong
     */

    public void addNewUserImage(int userID, String path) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(path));

        statement.executeQuery(
                "CALL add_new_user_image"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will add new task with the passed info.
     * @param userID the user id for the new task
     * @param title the title of the new task
     * @param dateTime the datetime of the new task YYYY-MM-dd HH:MM:ss
     * @param status the status of the new task
     * @param priority the priority of the new task
     * @throws SQLException exception in case something went wrong
     */

    public void addNewTask(int userID, String title, String dateTime, TaskStatus status, TaskPriority priority,boolean isStarred) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(title));
        parameters.add(addStringBetweenSingleQuote(dateTime));
        parameters.add(addStringBetweenSingleQuote(status.toString()));
        parameters.add(addStringBetweenSingleQuote(priority.toString()));
        parameters.add(isStarred?"true":"false");

        statement.executeQuery(
                "CALL add_new_task"
                + convertStringToParameters(parameters)
                + ";"
        );

    }


    /** This function will add a new note for the passed task id.
     * @param taskID the task id
     * @param note the new note
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will add a new tag to the passed task id.
     * @param taskID the task id
     * @param tag the new tag
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will delete the user with the passed id.
     * @param userID the user id
     * @throws SQLException exception in case something went wrong
     */

    public void deleteUser(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        statement.executeQuery(
                    "CALL delete_user"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will delete the image of the passed user id from the database.
     * @param userID the user id
     * @throws SQLException exception in case something went wrong
     */

    public void deleteUserImage(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        statement.executeQuery(
                "CALL delete_user_image"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will delete the task with the passed id.
     * @param taskID the task id
     * @throws SQLException exception in case something went wrong
     */

    public void deleteTask(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        statement.executeQuery(
                "CALL delete_task"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will delete the note for the passed id task.
     * @param taskID the task id
     * @param note the note
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will delete the passed tag for the passed task id.
     * @param taskID the task id
     * @param tag the tag string
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the user name for the passed user id.
     * @param userID user id
     * @param username the new user name
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the user password for the passed user id.
     * @param userID the user id
     * @param password the new password
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the user theme for the passed user id.
     * @param userID the user id
     * @param theme the new theme
     * @throws SQLException exception in case something went wrong
     */

    public void updateUserTheme(int userID, Theme theme) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(theme.toString()));

        statement.executeQuery(
                "CALL update_user_theme"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will update the image path for the passed user id in the database.
     * @param userID the user id
     * @param newPath the new path
     * @throws SQLException exception in case something went wrong
     */

    public void updateUserImage(int userID, String newPath) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(newPath));

        statement.executeQuery(
                "CALL update_user_image"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will update the task info for the passed task id.
     * @param taskID the task id
     * @param title the new title
     * @param dateTime the new datetime
     * @param status the new status
     * @param priority the new priority
     * @throws SQLException exception in case something went wrong
     */

    public void updateTask(int taskID, String title, String dateTime, TaskStatus status, TaskPriority priority, boolean isStarred) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(title));
        parameters.add(addStringBetweenSingleQuote(dateTime));
        parameters.add(addStringBetweenSingleQuote(status.toString()));
        parameters.add(addStringBetweenSingleQuote(priority.toString()));
        parameters.add(isStarred? "true": "false");

        statement.executeQuery(
                "CALL update_task"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will update the title for the passed task id.
     * @param taskID the task id
     * @param title the new title
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the datetime for the passed task id.
     * @param taskID the task id
     * @param datetime the new datetime
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the status for the passed task id.
     * @param taskID the task id
     * @param status the new status
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update the status is the task starred or not for the passed task id.
     * @param taskID the task id
     * @param isStarred the status of the task if it was starred or not
     * @throws SQLException exception in case something went wrong
     */

    public void updateTaskStarredStatus(int taskID, boolean isStarred) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(String.format("%b", isStarred));

        statement.executeQuery(
                "CALL update_task_is_starred"
                        + convertStringToParameters(parameters)
                        + ";"
        );

    }


    /** This function will update the priority for the passed task id.
     * @param taskID the task id
     * @param priority the new priority
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update a specific note for the passed task id.
     * @param taskID the task id
     * @param oldNote the old note string
     * @param newNote the new note string
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will update a specific tag for the passed task id.
     * @param taskID the task id
     * @param oldTag the old tag string
     * @param newTag the new tag string
     * @throws SQLException exception in case something went wrong
     */

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


    /** This function will return the last added id for users or tables.
     * @return it will return the last added id in the database
     * @throws SQLException exception in case something went wrong
     */

    public int getLastInsertedID() throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT last_insert_id();");

        if (resultSet.next())
            return resultSet.getInt(1);

        return -1;

    }



    /** This function will check if the passed user id exists in the database or note.
     * @param userID the user id
     * @return it will return true if the passed user id exists in the database, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfUserIDExists(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "SELECT user_id_exists"
                + convertStringToParameters(parameters)
                + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }


    /** This function will check if the passe user id has an image or not.
     * @param userID the user id
     * @return it will return true if the user has an image, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfUserImageExists(int userID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));

        ResultSet resultSet = statement.executeQuery(
                "SELECT user_image_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }



    /** This function will check if the passed user name exists in the database or not.
     * @param userName the user name
     * @return it will return true if the passed user name exists, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfUserNameExists(String userName) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(addStringBetweenSingleQuote(userName));

        ResultSet resultSet = statement.executeQuery(
                "SELECT user_name_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }


    /** This function will check if the passed task id exists in the database or not.
     * @param taskID the task id
     * @return it will return true if the passed task id exists, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfTaskIDExists(int taskID) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));

        ResultSet resultSet = statement.executeQuery(
                "SELECT task_id_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }


    /** This function will check if the passed task title exists on the passed day for the passed user id or not.
     * @param userID the user id
     * @param title the title of the task
     * @param datetime the day date of the task YYYY-MM-dd
     * @return it will return true if the passed task title exists on the passed day for the passed user id,
     * other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfTaskTitleExists(int userID, String title, String datetime) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", userID));
        parameters.add(addStringBetweenSingleQuote(title));
        parameters.add(addStringBetweenSingleQuote(datetime));


        ResultSet resultSet = statement.executeQuery(
                "SELECT task_title_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }



    /** This funciton will check if the passed note exists for the passed task id or not.
     * @param taskID the task id
     * @param note the note string
     * @return it will return true if the passed note exists for the passed task id, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfNoteExists(int taskID, String note) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(note));


        ResultSet resultSet = statement.executeQuery(
                "SELECT note_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }


    /** This function will check if the passed tag exists for the passed task id or not.
     * @param taskID the task id
     * @param tag the tag string
     * @return it will return true if the passed tag exists for the passed task id, other wise it will return false
     * @throws SQLException exception in case something went wrong
     */

    public boolean checkIfTagExists(int taskID, String tag) throws SQLException {

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add(String.format("%d", taskID));
        parameters.add(addStringBetweenSingleQuote(tag));


        ResultSet resultSet = statement.executeQuery(
                "SELECT tag_exists"
                        + convertStringToParameters(parameters)
                        + ";"
        );

        if (resultSet.next())
            return resultSet.getBoolean(1);

        return false;

    }


    /** This function will add the passed string between single quote.
     * @param string the string
     * @return it will return the string added between single quote
     */

    private String addStringBetweenSingleQuote(String string) {
        return "'" + string.replaceAll("'", "\\\\'") + "'";
    }


    /** This function will take a list of string then it will concat them in a parameters form.
     * @param strings the strings list
     * @return it will return a single string that contains all the strings in a form of parameters.
     */

    private String convertStringToParameters(List<String> strings) {

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


    /** This function will convert the passed tags list into a single text to be used in the task searching using tags.
     * @param tags the tags list
     * @return it will return the passed tags list into a single text to be used in the task searching using tags
     */

    private String convertListToRegexParam(List<String> tags) {

        StringBuilder tagsParma = new StringBuilder();

        for (int i = 0; i < tags.size(); i++) {
            tagsParma.append(tags.get(i));

            if (i != tags.size() - 1)
                tagsParma.append('|');

        }

        return tagsParma.toString();

    }

}
