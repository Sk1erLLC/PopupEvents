package club.sk1er.popupevents.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class HypixelGuildInviteEvent extends Event {

    private final String from;
    private final String guild;

    public HypixelGuildInviteEvent(String from, String guild) {
        this.from = from;
        this.guild = guild;
    }

    public String getFrom() {
        return from;
    }

    public String getGuild() {
        return guild;
    }
}
