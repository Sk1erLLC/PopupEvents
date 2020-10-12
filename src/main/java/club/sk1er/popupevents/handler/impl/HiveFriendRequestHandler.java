package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HiveFriendRequestEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;

public class HiveFriendRequestHandler extends AbstractChatHandler {
    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        Matcher matcher = regexTypePatternMap.get(ChatRegexType.HIVE_FRIEND_REQUEST).matcher(text);
        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HiveFriendRequestEvent(matcher.group("player")));
        }

        return false;
    }
}
