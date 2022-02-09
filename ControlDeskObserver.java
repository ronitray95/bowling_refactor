/* ControlDeskObserver.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 * 		$Log$
 *
 */

import models.ControlDeskEvent;

/**
 * Interface for classes that observe control desk events
 */
public interface ControlDeskObserver {
    void receiveControlDeskEvent(ControlDeskEvent ce);
}