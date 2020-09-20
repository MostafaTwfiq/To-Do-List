package GUI.ScreenManager;

import GUI.Observer.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Stack;

public class ScreenManager extends Observer {

    private final Stack<Parent> layoutsStack;
    private final HashMap<Parent, EventHandler<ActionEvent>> updateStyleEventsHM;
    private boolean lockScreen;

    public ScreenManager() {
        layoutsStack = new Stack<>();
        updateStyleEventsHM = new HashMap<>();
        lockScreen = false;
    }

    public void changeScreen(Parent layout, EventHandler<ActionEvent> updateStyleEvent) {

        if (lockScreen)
            return;

        layoutsStack.add(layout);
        updateStyleEventsHM.put(layout, updateStyleEvent);
        notifyObservers();

    }

    public void changeToLastScreen() {

        if (lockScreen || layoutsStack.size() <= 1)
            return;


        updateStyleEventsHM.remove(layoutsStack.pop());


        Parent lastLayout = layoutsStack.pop();
        changeScreen(lastLayout, updateStyleEventsHM.remove(lastLayout));

    }

    public Parent getCurrentLayout() {
        return layoutsStack.peek();
    }

    public void setLockScreen(boolean lockScreen) {
        this.lockScreen = lockScreen;
    }

    public boolean isLockScreen() {
        return lockScreen;
    }

    public void updateScreensStyle() {

        for (EventHandler<ActionEvent> event : updateStyleEventsHM.values())
            event.handle(null);

    }

}
