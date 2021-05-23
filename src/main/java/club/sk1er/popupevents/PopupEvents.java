package club.sk1er.popupevents;

import club.sk1er.popupevents.commands.PopupCommand;
import club.sk1er.popupevents.config.PopupConfig;
import club.sk1er.popupevents.handler.PopupHandlers;
import club.sk1er.popupevents.keybinds.KeybindAccept;
import club.sk1er.popupevents.keybinds.KeybindDeny;
import club.sk1er.popupevents.render.ConfirmationPopup;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = PopupEvents.MODID, name = PopupEvents.NAME, version = PopupEvents.VERSION)
public class PopupEvents {

    public static final String MODID = "popup_events";
    public static final String NAME = "PopupEvents";
    public static final String VERSION = "1.3.2";

    private final ConfirmationPopup confirmationPopup = new ConfirmationPopup();
    private KeyBinding keybindAccept, keybindDeny;
    private PopupConfig popupConfig;

    @Mod.Instance(MODID)
    public static PopupEvents instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ClientRegistry.registerKeyBinding(keybindAccept = new KeybindAccept());
        ClientRegistry.registerKeyBinding(keybindDeny = new KeybindDeny());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        popupConfig = new PopupConfig();
        popupConfig.preload();

        EssentialAPI.getCommandRegistry().registerCommand(new PopupCommand());

        PopupHandlers handlers = new PopupHandlers();
        handlers.postInit();

        MinecraftForge.EVENT_BUS.register(confirmationPopup);
    }

    public KeyBinding getKeybindAccept() {
        return keybindAccept;
    }

    public KeyBinding getKeybindDeny() {
        return keybindDeny;
    }

    public PopupConfig getPopupConfig() {
        return popupConfig;
    }
}
