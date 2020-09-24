package GUI.ScreenManager;

import GUI.IControllers;
import GUI.Observer.IObserver;
import GUI.Observer.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Stack;

public class ScreenManager extends Observer {

    private final Stack<IControllers> layoutsStack;
    private boolean lockScreen;

    public ScreenManager() {
        layoutsStack = new Stack<>();
        lockScreen = false;
    }

    public void changeScreen(IControllers controller) {

        if (lockScreen)
            return;

        layoutsStack.add(controller);
        notifyObservers();

    }

    public void changeToLastScreen() {

        if (lockScreen || layoutsStack.size() <= 1)
            return;


        layoutsStack.pop();

        changeScreen(layoutsStack.pop());

    }

    public void returnNumOfScreens(int num) {
        while (num-- > 0 && !layoutsStack.isEmpty())
            layoutsStack.pop();

        if (!layoutsStack.isEmpty())
            changeScreen(layoutsStack.pop());
        else
            notifyObservers();

    }

    public Parent getCurrentLayout() {
        if  (layoutsStack.isEmpty())
            return null;

        return layoutsStack.peek().getParent();
    }

    public void setLockScreen(boolean lockScreen) {
        this.lockScreen = lockScreen;
    }

    public boolean isLockScreen() {
        return lockScreen;
    }

    public void updateScreensStyle() {

        for (IControllers controller : layoutsStack)
            controller.updateStyle();

        for (IObserver observer : getObservers())
            observer.updateStyle();

    }

}
