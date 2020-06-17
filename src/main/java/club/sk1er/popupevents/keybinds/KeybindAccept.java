package club.sk1er.popupevents.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeybindAccept extends KeyBinding {
    public KeybindAccept() {
        super("Accept Request", Keyboard.KEY_Y, "Popup Events");
    }
}
