/* ControlDeskEvent.java
 *
 *  Version:
 *  		$Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

import java.util.Vector;

/**
 * Class that represents control desk event
 */
public class ControlDeskEvent {

    /**
     * A representation of the wait queue, containing party names
     */
    private final Vector<String> partyQueue;

    /**
     * Contstructor for the ControlDeskEvent
     *
     * @param partyQueue a Vector of Strings containing the names of the parties in the wait queue
     */

    public ControlDeskEvent(Vector<String> partyQueue) {
        this.partyQueue = partyQueue;
    }

    /**
     * Accessor for partyQueue
     *
     * @param key the key of the vertex being looked for.
     * @return a Vector of Strings representing the names of the parties in the wait queue
     */

    public Vector<String> getPartyQueue() {
        return partyQueue;
    }

}