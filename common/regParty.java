package common;

import java.io.IOException;
import java.util.Vector;

import models.Bowler;
import models.ControlDeskEvent;
import models.Party;

public class regParty {
    private final static Queue partyQueue= new Queue();
    private static Bowler registerPatron(String nickName) {
        Bowler patron = null;
        try {
            // only one patron / nick.... no dupes, no checks
            patron = BowlerFile.getBowlerInfo(nickName);
        } catch (IOException e) {
            System.err.println("Error..." + e);
        }
        return patron;
    }
    public static void addPartyQueue(Vector<String> partyNicks) {
        Vector<Bowler> partyBowlers = new Vector<>();
        for (String partyNick : partyNicks) {
            Bowler newBowler = registerPatron(partyNick);
            partyBowlers.add(newBowler);
        }
        Party newParty = new Party(partyBowlers);
        partyQueue.add(newParty);
        ControlDesk.publish(new ControlDeskEvent(getPartyQueue()));
    }

    public static Vector<String> getPartyQueue() {
        Vector<String> displayPartyQueue = new Vector<>();
        for (int i = 0; i < partyQueue.asVector().size(); i++) {
            String nextParty = ((Party) partyQueue.asVector().get(i)).getMembers()
                    .get(0)
                    .getNickName() + "'s Party";
            displayPartyQueue.addElement(nextParty);
        }
        return displayPartyQueue;
    }

        public static Queue fetchPartyQueue(){
            return partyQueue;
        }

}
