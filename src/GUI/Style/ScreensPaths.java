package GUI.Style;

import Main.Main;

public class ScreensPaths {

    private final String stageCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/Stage/StageSheet.css";
    private final String loginScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/LoginScreen/LoginSheet.css";
    private final String signUpScreenCssSheet = "GUI/Style/ThemesCss/" + Main.theme.getThemeName() + "/SignUpScreen/SignUpSheet.css";


    public String getStageFxml() {
        String stageFxml = "/GUI/ScreenManager/Stage/StageDesign.fxml";
        return stageFxml;
    }

    public String getStageCssSheet() {
        return stageCssSheet;
    }

    public String getLoginScreenFxml() {
        String loginScreenFxml = "/GUI/Screens/LoginScreen/LoginScreenStyle.fxml";
        return loginScreenFxml;
    }

    public String getLoginScreenCssSheet() {
        return loginScreenCssSheet;
    }

    public String getSignUpScreenFxml() {
        String signUpScreenFxml = "/GUI/Screens/SignUpScreen/SignUpScreenDesign.fxml";
        return signUpScreenFxml;
    }

    public String getSignUpScreenCssSheet() {
        return signUpScreenCssSheet;
    }

}
