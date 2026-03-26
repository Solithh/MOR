package me.mor.features.modules.client;

import me.mor.features.gui.HudEditorScreen;
import me.mor.features.modules.Module;

public class HudEditorModule extends Module {
    public HudEditorModule() {
        super("HudEditor", "Edit HUD element positions", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            disable();
            return;
        }
        mc.setScreen(HudEditorScreen.getInstance());
        disable();
    }
}

