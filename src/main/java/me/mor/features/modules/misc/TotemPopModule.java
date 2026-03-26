package me.mor.features.modules.misc;

import me.mor.event.impl.network.PacketEvent;
import me.mor.event.system.Subscribe;
import me.mor.features.modules.Module;
import me.mor.features.settings.Setting;
import me.mor.util.player.ChatUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class TotemPopModule extends Module {

    public Setting<Boolean> countSelf = bool("Count Self", true);

    private final Map<String, Integer> popCounts = new HashMap<>();

    public TotemPopModule() {
        super("TotemPop", "Notifies you when players in render distance pop a totem", Category.MISC);
    }

    @Override
    public void onDisable() {
        popCounts.clear();
    }

    @Subscribe
    private void onPacketReceive(PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof ClientboundEntityEventPacket packet)) return;
        if (packet.getEventId() != 35) return;

        if (mc.level == null || mc.player == null) return;

        Entity entity = packet.getEntity(mc.level);
        if (!(entity instanceof Player player)) return;

        String name = player.getName().getString();

        if (!countSelf.getValue() && name.equals(mc.player.getName().getString())) return;

        int count = popCounts.getOrDefault(name, 0) + 1;
        popCounts.put(name, count);

        ChatUtil.sendMessage(Component.literal(
            "§e" + name + "§r has popped §c" + count + "§r totem" + (count == 1 ? "" : "s")
        ), "TotemPop");
    }
}
