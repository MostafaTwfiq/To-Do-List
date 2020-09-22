package GUI.Style;

import GUI.Style.Style.DarkTheme;
import GUI.Style.Style.LightTheme;
import GUI.Style.Style.Style;
import GUI.Style.Style.Theme;

public class StyleFactory {


    public Style generateTheme(Theme theme) {

        switch (theme) {
            case LightTheme:
                return new LightTheme();
            case DarkTheme:
                return new DarkTheme();
        }

        return null;

    }
}
