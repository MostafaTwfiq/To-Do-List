package DataClasses;


public class User {

    private short userID;
    private String userName;
    private String userImagePath;
    private final String defaultUserImagePath = "resources/Users/defaultUserProfileImage.png";

    public User(short userID, String userName) {

        if (userName == null)
            throw new IllegalArgumentException();

        this.userID = userID;
        this.userName = userName;
        this.userImagePath = defaultUserImagePath;

    }

    public User(short userID, String userName, String userImagePath) {

        if (userName == null || userImagePath == null)
            throw new IllegalArgumentException();

        this.userID = userID;
        this.userName = userName;
        this.userImagePath = userImagePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        if (userName == null)
            throw new IllegalArgumentException();

        this.userName = userName;

    }

    public short getUserID() {
        return userID;
    }

    public void setUserID(short userID) {

        if (userID < 0)
            throw new IllegalArgumentException();

        this.userID = userID;

    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {

        if (userImagePath == null)
            throw new IllegalArgumentException();

        this.userImagePath = userImagePath;

    }

    public String getDefaultUserImagePath() {
        return defaultUserImagePath;
    }

}
