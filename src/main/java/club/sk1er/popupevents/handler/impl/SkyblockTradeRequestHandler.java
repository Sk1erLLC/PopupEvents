package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.SkyblockTradeRequestEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Locale;
import java.util.regex.Matcher;

public class SkyblockTradeRequestHandler extends AbstractChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.toLowerCase(Locale.ENGLISH).contains("sent you a trade request.")) return false;
        Matcher matcher = regexTypePatternMap.get(ChatRegexType.SKYBLOCK_TRADE_REQUEST).matcher(text);

        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new SkyblockTradeRequestEvent(matcher.group("player")));
        }

        return false;
    }
}
