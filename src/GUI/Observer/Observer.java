package GUI.Observer;

import java.util.List;
import java.util.Vector;

public abstract class Observer {

    private List<IObserver> observers;

    public Observer() {
        observers = new Vector<>();
    }

    public void addObserver(IObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.add(observer);

    }

    public void deleteObserver(IObserver observer) {

        if (observer == null)
            throw new IllegalArgumentException();

        observers.remove(observer);

    }

    public void notifyObservers() {

        for (IObserver observer : observers)
            observer.update();

    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<IObserver> observers) {

        if (observers == null)
            throw new IllegalArgumentException();

        this.observers = observers;
    }
}
