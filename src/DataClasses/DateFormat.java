package DataClasses;

public abstract class DateFormat {

    private static final String dateTimeRegex = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01]) ([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    private static final String dateRegex = "(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])";
    private static final String dateFormat = "yyyy-MM-dd";

    public static String getDateTimeRegex() {
        return dateTimeRegex;
    }

    public static String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public static String getDateRegex() {
        return dateRegex;
    }

    public static String getDateFormat() {
        return dateFormat;
    }

}
