package club.sk1er.popupevents.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class SkyblockTradeRequestEvent extends Event {

    private final String username;

    public SkyblockTradeRequestEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
