package club.sk1er.popupevents.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class HiveFriendRequestEvent extends Event {

    private final String from;

    public HiveFriendRequestEvent(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
}
