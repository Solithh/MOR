package me.mor.manager;

import me.mor.MOR;
import me.mor.event.Stage;
import me.mor.event.impl.entity.DeathEvent;
import me.mor.event.impl.entity.player.TickEvent;
import me.mor.event.impl.entity.player.UpdateWalkingPlayerEvent;
import me.mor.event.impl.input.KeyInputEvent;
import me.mor.event.impl.network.ChatEvent;
import me.mor.event.impl.network.PacketEvent;
import me.mor.event.impl.render.Render2DEvent;
import me.mor.event.impl.render.Render3DEvent;
import me.mor.event.system.Subscribe;
import me.mor.features.Feature;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.BrandPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.world.entity.player.Player;

public class EventManager extends Feature {
    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (nullCheck())
            return;
        MOR.moduleManager.onTick();
        for (Player player : mc.level.players()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            EVENT_BUS.post(new DeathEvent(player));
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (nullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            MOR.speedManager.update();
            MOR.rotationManager.updateRotations();
            MOR.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            MOR.rotationManager.restoreRotations();
            MOR.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        MOR.serverManager.onPacketReceived();
        if (event.getPacket() instanceof ClientboundSetTimePacket)
            MOR.serverManager.update();
        if (event.getPacket() instanceof ClientboundCustomPayloadPacket(CustomPacketPayload payload)
                && payload instanceof BrandPayload(String brand)) {
            MOR.serverManager.setServerBrand(brand);
        }
    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        MOR.moduleManager.onRender3D(event);
    }

    @Subscribe
    public void onRenderGameOverlayEvent(Render2DEvent event) {
        MOR.moduleManager.onRender2D(event);
    }

    @Subscribe
    public void onKeyInput(KeyInputEvent event) {
        MOR.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe
    public void onChatSent(ChatEvent event) {
        String message = event.getMessage();
        if (!message.startsWith(MOR.commandManager.getCommandPrefix())) {
            return;
        }
        event.cancel();
        MOR.commandManager.onChatSent(message);
    }
}