package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HypixelPartyInviteEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;

public class PartyInviteHandler extends AbstractChatHandler {

    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.contains("has invited you to join")) {
            return false;
        }

        Matcher matcher = regexTypePatternMap.get(ChatRegexType.PARTY_INVITE).matcher(text);

        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HypixelPartyInviteEvent(matcher.group("player")));
        }

        return false;
    }
}
