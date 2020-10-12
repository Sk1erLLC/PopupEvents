package club.sk1er.popupevents.handler;

import club.sk1er.popupevents.handler.impl.*;
import net.minecraft.command.ICommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class PopupHandlers {

    private final List<AbstractChatHandler> chatHandlers;
    private final GeneralChatHandler generalChatHandler;

    public PopupHandlers() {
        chatHandlers = new ArrayList<>();
        register(generalChatHandler = new GeneralChatHandler(chatHandlers));
        registerChatHandler(new FriendRequestHandler());
        registerChatHandler(new PartyInviteHandler());
        registerChatHandler(new SkyblockTradeRequestHandler());
        registerChatHandler(new DuelRequestHandler());
        registerChatHandler(new GuildInviteHandler());
        registerChatHandler(new GuildPartyHandler());
        registerChatHandler(new HiveFriendRequestHandler());
        registerChatHandler(new HivePartyRequestHandler());
        registerChatHandler(new MineplexFriendRequestHandler());

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void postInit() {
        generalChatHandler.post();
    }

    private void registerChatHandler(AbstractChatHandler chatHandler) {
        register(chatHandler);
        chatHandlers.add(chatHandler);
    }

    private void register(Object object) {
        MinecraftForge.EVENT_BUS.register(object);
    }

    @SubscribeEvent
    public void tick(TickEvent event) {
        IntegratedServer server = FMLClientHandler.instance().getClient().getIntegratedServer();
        if (server == null) return;

        ICommandManager commandManager = server.getCommandManager();
        if (commandManager == null) return;

        MinecraftForge.EVENT_BUS.unregister(PopupHandlers.class);
    }
}
