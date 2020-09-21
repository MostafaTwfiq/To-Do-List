package GUI.Style;

public class StyleFactory {


    public Style generateTheme(Themes theme) {

        switch (theme) {
            case LightTheme:
                return new LightTheme();
            case DarkTheme:
                return new DarkTheme();
        }

        return null;

    }
}
