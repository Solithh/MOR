package me.mor.util.traits;

import me.mor.event.system.EventBus;
import net.minecraft.client.Minecraft;

public interface Util {
    Minecraft mc = Minecraft.getInstance();
    EventBus EVENT_BUS = new EventBus();
}
