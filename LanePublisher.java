import models.LaneEvent;

import java.util.Observable;

public class LanePublisher extends Observable {

    public void publish(LaneEvent le) {
        setChanged();
        notifyObservers(le);
    }
}
