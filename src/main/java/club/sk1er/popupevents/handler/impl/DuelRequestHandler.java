package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HypixelDuelRequestEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Locale;
import java.util.regex.Matcher;

public class DuelRequestHandler extends AbstractChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.toLowerCase(Locale.ENGLISH).contains("has invited you to")) return false;
        Matcher matcher = regexTypePatternMap.get(ChatRegexType.DUEL_REQUEST).matcher(text);

        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HypixelDuelRequestEvent(matcher.group("player"), matcher.group("gametype")));
        }

        return false;
    }
}
