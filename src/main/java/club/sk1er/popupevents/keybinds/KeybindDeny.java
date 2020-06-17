package club.sk1er.popupevents.keybinds;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeybindDeny extends KeyBinding {
    public KeybindDeny() {
        super("Deny Request", Keyboard.KEY_N, "Popup Events");
    }
}
