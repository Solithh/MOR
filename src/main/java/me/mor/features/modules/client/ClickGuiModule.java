package me.mor.features.modules.client;

import me.mor.MOR;
import me.mor.event.impl.ClientEvent;
import me.mor.event.system.Subscribe;
import me.mor.features.commands.Command;
import me.mor.features.gui.MORGui;
import me.mor.features.modules.Module;
import me.mor.features.settings.Setting;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class ClickGuiModule extends Module {
    private static ClickGuiModule INSTANCE;

    public Setting<String> prefix = str("Prefix", ".");
    public Setting<Color> color = color("Color", 0, 0, 255, 180);
    public Setting<Color> topColor = color("TopColor", 0, 0, 150, 240);
    public Setting<Boolean> rainbow = bool("Rainbow", false);
    public Setting<Integer> rainbowHue = num("Delay", 240, 0, 600);
    public Setting<Float> rainbowBrightness = num("Brightness", 150.0f, 1.0f, 255.0f);
    public Setting<Float> rainbowSaturation = num("Saturation", 150.0f, 1.0f, 255.0f);

    public ClickGuiModule() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT);
        setBind(GLFW.GLFW_KEY_RIGHT_SHIFT);
        rainbowHue.setVisibility(v -> rainbow.getValue());
        rainbowBrightness.setVisibility(v -> rainbow.getValue());
        rainbowSaturation.setVisibility(v -> rainbow.getValue());
        INSTANCE = this;
    }

    @Subscribe
    public void onSettingChange(ClientEvent event) {
        if (event.getType() == ClientEvent.Type.SETTING_UPDATE && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                MOR.commandManager.setCommandPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to {global} %s", MOR.commandManager.getCommandPrefix());
            }
            if (event.getSetting().equals(this.color)) {
                MOR.colorManager.setColor(this.color.getPlannedValue());
            }
        }
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            return;
        }
        mc.setScreen(MORGui.getClickGui());
    }

    @Override
    public void onLoad() {
        MOR.colorManager.setColor(this.color.getValue());
        MOR.commandManager.setCommandPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(ClickGuiModule.mc.screen instanceof MORGui)) {
            this.disable();
        }
    }

    public static ClickGuiModule getInstance() {
        return INSTANCE;
    }
}