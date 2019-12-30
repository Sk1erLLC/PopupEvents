package club.sk1er.popupevents.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class HypixelDuelRequestEvent extends Event {

    private final String username;
    private final String gameType;

    public HypixelDuelRequestEvent(String username, String gameType) {
        this.username = username;
        this.gameType = gameType;
    }

    public String getUsername() {
        return username;
    }

    public String getGame() {
        return gameType;
    }
}
