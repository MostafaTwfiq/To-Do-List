package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.*;
import javafx.scene.paint.Color;

public class LightTheme extends Style {

    public LightTheme() {

        super(

                "/Themes/LightTheme/",
                new StageTheme(
                        Color.rgb(209, 69, 59), //#D1453B
                        Color.rgb(79, 82, 84) //#4f5254
                ),
                new SearchBoxTheme(
                        Color.rgb(255,255,255),
                        Color.rgb(0, 0, 0),
                        Color.rgb(0,0,0)
                ),
                new MultiProgressBarTheme(
                        Color.rgb(60, 155, 13),
                        Color.rgb(254, 207, 5),
                        Color.rgb(255, 144, 0),
                        Color.rgb(209, 69, 59)
                ),
                new ProgressBarTheme(
                        Color.rgb(255,255,255),
                        Color.rgb(61,61,61),
                        Color.rgb(113, 113, 113)
                ),
                Color.rgb(0, 0, 0),
                new PrioritiesColors(
                        Color.rgb(209, 69, 59),
                        Color.rgb(255, 144, 0),
                        Color.rgb(254, 207, 5),
                        Color.rgb(60, 155, 13)
                ),
                new PopUpOptionsTheme(
                        Color.BLACK,
                        Color.WHITE
                )

        );

    }

    @Override
    public String getThemeName() {
        return "LightTheme";
    }
}
