package club.sk1er.popupevents.commands;

import club.sk1er.mods.core.ModCore;
import club.sk1er.popupevents.PopupEvents;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public class PopupCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "popupevents";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        ModCore.getInstance().getGuiHandler().open(PopupEvents.instance.getPopupConfig().gui());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("popups");
    }
}
