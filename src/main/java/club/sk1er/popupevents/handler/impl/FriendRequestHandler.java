package club.sk1er.popupevents.handler.impl;

import club.sk1er.popupevents.events.HypixelFriendRequestEvent;
import club.sk1er.popupevents.handler.AbstractChatHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.regex.Matcher;

public class FriendRequestHandler extends AbstractChatHandler {

    @Override
    public boolean chatReceived(IChatComponent component, String text) {
        if (!text.toLowerCase().contains("friend request")) {
            return false;
        }

        Matcher matcher = regexTypePatternMap.get(ChatRegexType.FRIEND_REQUEST).matcher(text);
        if (matcher.find()) {
            MinecraftForge.EVENT_BUS.post(new HypixelFriendRequestEvent(matcher.group("player")));
        }

        return false;
    }
}
