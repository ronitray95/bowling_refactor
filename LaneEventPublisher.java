import models.LaneEvent;

import java.util.Observable;

public class LaneEventPublisher extends Observable {

    public void publish(LaneEvent le) {
        setChanged();
        notifyObservers(le);
    }
}
