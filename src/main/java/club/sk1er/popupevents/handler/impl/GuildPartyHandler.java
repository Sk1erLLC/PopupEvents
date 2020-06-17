package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HypixelGuildPartyEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Locale;
import java.util.regex.Matcher;

public class GuildPartyHandler extends AbstractChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.toLowerCase(Locale.ENGLISH).contains("invited all members")) return false;
        Matcher matcher = regexTypePatternMap.get(ChatRegexType.GUILD_PARTY).matcher(text);

        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HypixelGuildPartyEvent(matcher.group("player"), matcher.group("guild")));
        }

        return false;
    }
}
