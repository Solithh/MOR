package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.event.impl.network.ChatEvent;
import me.alpha432.oyvey.event.impl.network.PacketEvent;
import me.alpha432.oyvey.event.system.Subscribe;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;

public class KillSayModule extends Module {

    public Setting<Boolean> killSayEnabled = bool("Kill Say", true);
    public Setting<String> killMessage = string("Kill Message", "ez");

    public Setting<Boolean> suffixEnabled = bool("Suffix", true);
    public Setting<String> suffixText = string("Suffix Text", "suffix");

    public KillSayModule() {
        super("KillSay", "Sends a message on kill and appends a suffix to chat", Category.MISC);
    }

    @Subscribe
    private void onPacketReceive(PacketEvent.Receive event) {
        if (!killSayEnabled.getValue()) return;
        if (!(event.getPacket() instanceof ClientboundSystemChatPacket packet)) return;

        String msg = packet.content().getString();
        String name = mc.player != null ? mc.player.getName().getString() : "";
        if (name.isEmpty()) return;

        if (isKillMessage(msg, name)) {
            mc.player.connection.sendChat(killMessage.getValue());
        }
    }

    @Subscribe
    private void onChat(ChatEvent event) {
        if (!suffixEnabled.getValue()) return;
        String msg = event.getMessage();
        if (msg.startsWith("/")) return;
        event.setMessage(msg + " " + suffixText.getValue());
    }

    private boolean isKillMessage(String msg, String playerName) {
        if (!msg.contains(playerName)) return false;
        String lower = msg.toLowerCase();
        String lowerName = playerName.toLowerCase();

        int byIndex = lower.lastIndexOf(" by ");
        int usingIndex = lower.lastIndexOf(" using ");

        if (byIndex != -1 && lower.indexOf(lowerName, byIndex) != -1) return true;
        if (usingIndex != -1 && lower.indexOf(lowerName, usingIndex) != -1) return true;

        return false;
    }
}
