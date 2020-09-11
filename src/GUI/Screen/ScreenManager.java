package GUI.Screen;

import GUI.Observer.Observer;
import javafx.scene.Parent;

import java.util.Stack;

public class ScreenManager extends Observer {

    private Stack<Parent> layoutsStack;
    private boolean lockScreen;

    public ScreenManager() {
        layoutsStack = new Stack<>();
        lockScreen = false;
    }

    public void changeScreen(Parent layout) {

        if (lockScreen)
            return;

        layoutsStack.add(layout);
        notifyObservers();

    }

    public void changeToLastScreen() {

        if (lockScreen || layoutsStack.size() <= 1)
            return;

        layoutsStack.pop();
        changeScreen(layoutsStack.pop());

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

}
