package GUI.Style.Style.ExtraComponents;

import javafx.scene.paint.Color;

public class PrioritiesColors {

    private final Color IMPORTANT_AND_URGENT_C;
    private final Color IMPORTANT_AND_NOT_URGENT_C;
    private final Color NOT_IMPORTANT_AND_URGENT_C;
    private final Color NOT_IMPORTANT_AND_NOT_URGENT_C;

    public PrioritiesColors(
            Color IMPORTANT_AND_URGENT_C,
            Color IMPORTANT_AND_NOT_URGENT_C,
            Color NOT_IMPORTANT_AND_URGENT_C,
            Color NOT_IMPORTANT_AND_NOT_URGENT_C) {

        this.IMPORTANT_AND_URGENT_C = IMPORTANT_AND_URGENT_C;
        this.IMPORTANT_AND_NOT_URGENT_C = IMPORTANT_AND_NOT_URGENT_C;
        this.NOT_IMPORTANT_AND_URGENT_C = NOT_IMPORTANT_AND_URGENT_C;
        this.NOT_IMPORTANT_AND_NOT_URGENT_C = NOT_IMPORTANT_AND_NOT_URGENT_C;

    }

    public Color getIMPORTANT_AND_URGENT_C() {
        return IMPORTANT_AND_URGENT_C;
    }

    public Color getIMPORTANT_AND_NOT_URGENT_C() {
        return IMPORTANT_AND_NOT_URGENT_C;
    }

    public Color getNOT_IMPORTANT_AND_URGENT_C() {
        return NOT_IMPORTANT_AND_URGENT_C;
    }

    public Color getNOT_IMPORTANT_AND_NOT_URGENT_C() {
        return NOT_IMPORTANT_AND_NOT_URGENT_C;
    }

}
