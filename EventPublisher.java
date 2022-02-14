import models.LaneEvent;

import java.util.Observable;

public class EventPublisher extends Observable {

    public void publish(Object le) {
        setChanged();
        notifyObservers(le);
    }
}
