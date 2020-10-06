package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.*;
import javafx.scene.paint.Color;

public class PurkTheme extends Style {

    public PurkTheme() {

        super(

                "/Themes/PurkTheme/",
                new StageTheme(
                        Color.rgb(209, 69, 59), //#D1453B
                        Color.rgb(79, 82, 84) //#4f5254
                ),
                new SearchBoxTheme(
                        Color.WHITE,
                        Color.rgb(0, 0, 0),
                        Color.rgb(0,0,0)
                ),
                new MultiProgressBarTheme(
                        Color.rgb(0, 148, 50),
                        Color.rgb(255, 195, 18),
                        Color.rgb(238, 90, 36),
                        Color.rgb(239, 83, 80)
                ),
                new ProgressBarTheme(
                        Color.WHITE,
                        Color.rgb(147,61,121),
                        Color.rgb(39, 44, 46)
                ),
                Color.rgb(255, 255, 255),
                new PrioritiesColors(
                        Color.rgb(239, 83, 80),
                        Color.rgb(238, 90, 36),
                        Color.rgb(255, 195, 18),
                        Color.rgb(0, 148, 50)
                ),
                new PopUpOptionsTheme(
                        Color.WHITE,
                        Color.rgb(39, 44, 46)
                )

        );

    }

    @Override
    public String getThemeName() {
        return "PurkTheme";
    }
}
