package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HypixelGuildInviteEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.Locale;
import java.util.regex.Matcher;

public class GuildInviteHandler extends AbstractChatHandler {

    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.toLowerCase().contains("their guild,")) {
            return false;
        }

        Matcher matcher = regexTypePatternMap.get(ChatRegexType.GUILD_INVITE).matcher(text);

        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HypixelGuildInviteEvent(matcher.group("player"), matcher.group("guild")));
        }

        return false;
    }
}
