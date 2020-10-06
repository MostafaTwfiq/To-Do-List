package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.*;
import javafx.scene.paint.Color;

public class DarkTheme extends Style {

    public DarkTheme() {

        super(

                "/Themes/DarkTheme/",
                new StageTheme(
                        Color.rgb(209, 69, 59), //#D1453B
                        Color.rgb(79, 82, 84) //#4f5254
                ),
                new SearchBoxTheme(
                        Color.rgb(190, 190, 190),
                        Color.rgb(0, 0, 0),
                        Color.rgb(0,0,0)
                ),
                new MultiProgressBarTheme(
                        Color.rgb(42, 139, 68),
                        Color.rgb(218, 180, 29),
                        Color.rgb(218, 119, 32),
                        Color.rgb(200, 62, 82)
                ),
                new ProgressBarTheme(
                        Color.rgb(190, 190, 190),
                        Color.rgb(47, 170, 219),
                        Color.rgb(39, 44, 46)
                ),
                Color.rgb(207, 207, 207),
                new PrioritiesColors(
                        Color.rgb(200, 62, 82),
                        Color.rgb(218, 119, 32),
                        Color.rgb(218, 180, 29),
                        Color.rgb(42, 139, 68)
                ),
                new PopUpOptionsTheme(
                        Color.rgb(207, 207, 207),
                        Color.rgb(39, 44, 46)
                )

        );

    }


    @Override
    public String getThemeName() {
        return "DarkTheme";
    }

}
