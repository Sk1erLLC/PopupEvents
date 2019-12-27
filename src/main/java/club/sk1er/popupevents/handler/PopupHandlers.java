package club.sk1er.popupevents.handler;

import club.sk1er.popupevents.handler.impl.FriendRequestHandler;
import club.sk1er.popupevents.handler.impl.PartyInviteHandler;
import club.sk1er.popupevents.handler.impl.SkyblockTradeRequestHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class PopupHandlers {

    private List<AbstractChatHandler> chatHandlers;
    private GeneralChatHandler generalChatHandler;

    public PopupHandlers() {
        chatHandlers = new ArrayList<>();
        register(generalChatHandler = new GeneralChatHandler(chatHandlers));
        registerChatHandler(new FriendRequestHandler());
        registerChatHandler(new PartyInviteHandler());
        registerChatHandler(new SkyblockTradeRequestHandler());

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

    public List<AbstractChatHandler> getChatHandlers() {
        return chatHandlers;
    }

    public GeneralChatHandler getGeneralChatHandler() {
        return generalChatHandler;
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
