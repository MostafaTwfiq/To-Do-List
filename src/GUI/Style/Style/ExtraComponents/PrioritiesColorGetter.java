package GUI.Style.Style.ExtraComponents;

import DataClasses.TaskStatus.TaskPriority;
import GUI.Style.Style.Style;
import javafx.scene.paint.Color;

public class PrioritiesColorGetter {

    public Color getPriorityColor(Style theme, TaskPriority taskPriority) {

        PrioritiesColors  prioritiesColors = theme.getPrioritiesColors();

        switch (taskPriority) {
            case IMPORTANT_AND_URGENT:
                return prioritiesColors.getIMPORTANT_AND_URGENT_C();
            case IMPORTANT_AND_NOT_URGENT:
                return prioritiesColors.getIMPORTANT_AND_NOT_URGENT_C();
            case NOT_IMPORTANT_AND_URGENT:
                return prioritiesColors.getNOT_IMPORTANT_AND_URGENT_C();
            case NOT_IMPORTANT_AND_NOT_URGENT:
                return prioritiesColors.getNOT_IMPORTANT_AND_NOT_URGENT_C();
        }

        return null;

    }
}
