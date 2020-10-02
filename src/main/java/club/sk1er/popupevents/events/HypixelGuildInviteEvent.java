package club.sk1er.popupevents.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class HypixelGuildInviteEvent extends Event {

    private final String command;
    private final String guild;

    public HypixelGuildInviteEvent(String command, String guild) {
        this.command = command;
        this.guild = guild;
    }

    public String getCommand() {
        return command;
    }

    public String getGuild() {
        return guild;
    }
}
