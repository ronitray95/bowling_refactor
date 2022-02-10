import java.util.Observable;
import models.LaneEvent;

public class LanePublisher extends Observable {

	public void publish(LaneEvent le) {
        setChanged();
        notifyObservers(le);
    }
}
