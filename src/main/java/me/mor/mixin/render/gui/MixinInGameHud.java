package me.mor.mixin.render.gui;

import me.mor.event.impl.render.Render2DEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.mor.util.traits.Util.EVENT_BUS;

@Mixin(Gui.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At("RETURN"))
    public void render(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen()) return;

        Render2DEvent event = new Render2DEvent(context, tickCounter.getGameTimeDeltaPartialTick(true));
        EVENT_BUS.post(event);
    }

}
