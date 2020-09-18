package Style;

import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class LightTheme extends Style {

    public LightTheme() {

        super(

                "resources/Themes/LightTheme/",
                new StageTheme(
                        Color.rgb(209, 69, 59), //#D1453B
                        Color.rgb(79, 82, 84) //#4f5254
                )

        );

    }

    @Override
    public String getThemeName() {
        return "LightTheme";
    }

}
