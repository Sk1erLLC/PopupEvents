package club.sk1er.popupevents.handler;

import club.sk1er.mods.core.util.JsonHolder;
import net.minecraft.util.IChatComponent;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractChatHandler {

    protected static Map<ChatRegexType, Pattern> regexTypePatternMap;

    public abstract boolean chatReceived(IChatComponent component, String text);

    void callback(JsonHolder data) {
    }

    public enum ChatRegexType {
        FRIEND_REQUEST,
        PARTY_INVITE,
        SKYBLOCK_TRADE_REQUEST,
        DUEL_REQUEST,
        GUILD_INVITE,
        GUILD_PARTY,
        HIVE_FRIEND_REQUEST,
        HIVE_PARTY_REQUEST
    }
}
