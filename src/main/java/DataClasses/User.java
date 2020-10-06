package DataClasses;


import GUI.Style.Style.Theme;

public class User {

    private short userID;
    private String userName;
    private String userImagePath;
    private final String defaultUserImagePath = "resources/Users/defaultUserProfileImage.png";
    private Theme theme;

    public User(short userID, String userName, Theme theme) {

        if (userName == null)
            throw new IllegalArgumentException();

        this.userID = userID;
        this.userName = userName;
        this.userImagePath = defaultUserImagePath;
        this.theme = theme;

    }

    public User(short userID, String userName, String userImagePath, Theme theme) {

        if (userName == null || userImagePath == null)
            throw new IllegalArgumentException();

        this.userID = userID;
        this.userName = userName;
        this.userImagePath = userImagePath;
        this.theme = theme;
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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {

        if (theme == null)
            throw new IllegalArgumentException();

        this.theme = theme;
    }
}
