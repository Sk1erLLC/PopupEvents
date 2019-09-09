package club.sk1er.popupevents;

import club.sk1er.popupevents.handler.PopupHandlers;
import club.sk1er.popupevents.render.ConfirmationPopup;
import club.sk1er.popupevents.utils.Sk1erMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = PopupEvents.MODID, name = PopupEvents.NAME, version = PopupEvents.VERSION)
public class PopupEvents {

    public static final String MODID = "popup_events";
    public static final String NAME = "PopupEvents";
    public static final String VERSION = "1.0";

    private final ConfirmationPopup confirmationPopup = new ConfirmationPopup();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new Sk1erMod(MODID, VERSION, NAME).checkStatus();
        PopupHandlers handlers = new PopupHandlers();
        handlers.postInit();

        MinecraftForge.EVENT_BUS.register(confirmationPopup);
    }
}
