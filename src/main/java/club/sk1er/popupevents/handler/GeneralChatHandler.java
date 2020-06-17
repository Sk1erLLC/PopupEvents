package club.sk1er.popupevents.handler;

import club.sk1er.mods.core.util.JsonHolder;
import com.google.gson.JsonParser;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Pattern;

public class GeneralChatHandler {

    private static GeneralChatHandler instance;
    private final List<AbstractChatHandler> handlers;
    private boolean posted;

    GeneralChatHandler(List<AbstractChatHandler> handlers) {
        this.handlers = handlers;
        instance = this;
    }

    @SubscribeEvent
    public void chatEvent(ClientChatReceivedEvent event) {
        boolean state = true;

        if (!posted) {
            return;
        }

        for (AbstractChatHandler chatHandler : handlers) {
            try {
                state = chatHandler.chatReceived(event.message, strip(event.message)) && state;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void post() {
        JsonHolder data = new JsonHolder(new JsonParser().parse(new InputStreamReader(GeneralChatHandler.class.getResourceAsStream("/remoteresources/chat_regex.json"))).getAsJsonObject());

        AbstractChatHandler.regexTypePatternMap = new EnumMap<>(AbstractChatHandler.ChatRegexType.class);

        for (AbstractChatHandler.ChatRegexType type : AbstractChatHandler.ChatRegexType.values()) {
            if (!data.has(type.name().toLowerCase())) {
                continue;
            }

            AbstractChatHandler.regexTypePatternMap.put(type, Pattern.compile(data.optString(type.name().toLowerCase())));
        }

        posted = true;

        for (AbstractChatHandler chatHandler : handlers) {
            chatHandler.callback(data);
        }
    }

    private String strip(IChatComponent message) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(message.getUnformattedText());
    }

    public static GeneralChatHandler instance() {
        return instance;
    }
}
