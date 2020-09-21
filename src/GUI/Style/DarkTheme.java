package GUI.Style;

import javafx.scene.paint.Color;

public class DarkTheme extends Style {

    public DarkTheme() {

        super(

                "resources/Themes/DarkTheme/",
                new StageTheme(
                        Color.rgb(209, 69, 59), //#D1453B
                        Color.rgb(79, 82, 84) //#4f5254
                )

        );

    }


    @Override
    public String getThemeName() {
        return "DarkTheme";
    }

}
