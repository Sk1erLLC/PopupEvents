package club.sk1er.popupevents.commands;

import club.sk1er.popupevents.PopupEvents;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.utils.GuiUtil;

import java.util.Objects;

public class PopupCommand extends Command {
    public PopupCommand() {
        super("popupevents");
    }

    @DefaultHandler
    public void handle() {
        GuiUtil.open(Objects.requireNonNull(PopupEvents.instance.getPopupConfig().gui()));
    }
}
