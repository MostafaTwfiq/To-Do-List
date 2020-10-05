package GUI.Style;

import GUI.Style.Style.*;

public class StyleFactory {


    public Style generateTheme(Theme theme) {

        switch (theme) {
            case LightTheme:
                return new LightTheme();
            case DarkTheme:
                return new DarkTheme();
            case PurkTheme:
                return new PurkTheme();
        }

        return null;

    }
}
