package me.mor.manager;

import me.mor.features.modules.client.ClickGuiModule;
import me.mor.util.ColorUtil;

import java.awt.*;

public class ColorManager {
    private Color color = new Color(0, 0, 255, 180);

    public void init() {
        ClickGuiModule ui = ClickGuiModule.getInstance();
        setColor(ui.color.getValue());
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getColorAsInt() {
        return this.color.getRGB();
    }

    public int getColorAsIntFullAlpha() {
        return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255).getRGB();
    }

    public int getColorWithAlpha(float offset, int alpha) {
        if (ClickGuiModule.getInstance().rainbow.getValue()) {
            return ColorUtil.rainbow((int) (offset / 10f * ClickGuiModule.getInstance().rainbowHue.getValue())).getRGB();
        }
        return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha).getRGB();
    }
}
