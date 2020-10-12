package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HivePartyRequestEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;

public class HivePartyRequestHandler extends AbstractChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        Matcher matcher = regexTypePatternMap.get(ChatRegexType.HIVE_PARTY_REQUEST).matcher(text);
        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HivePartyRequestEvent(matcher.group("player")));
        }

        return false;
    }
}
