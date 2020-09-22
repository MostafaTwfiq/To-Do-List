package GUI.Style.Style;

import GUI.Style.Style.ExtraComponents.*;
import javafx.scene.paint.Color;

public class DarkTheme extends Style {

    public DarkTheme() {

        super(

                "resources/Themes/DarkTheme/",
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
                        Color.rgb(98, 220, 165),
                        Color.rgb(247, 248, 121),
                        Color.rgb(230,126,34),
                        Color.rgb(226, 103, 90)
                ),
                new ProgressBarTheme(
                        Color.rgb(149,165,166),
                        Color.rgb(236,240,241),
                        Color.rgb(0,0,0)
                ),
                Color.rgb(0, 0, 0),
                new PrioritiesColors(
                        Color.rgb(226, 103, 90),
                        Color.rgb(230,126,34),
                        Color.rgb(247, 248, 121),
                        Color.rgb(98, 220, 165)
                )

        );

    }


    @Override
    public String getThemeName() {
        return "DarkTheme";
    }

}
