package me.mor.mixin.network;

import me.mor.event.impl.network.ChatEvent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.mor.util.traits.Util.EVENT_BUS;

@Mixin(ClientPacketListener.class)
public class MixinClientPlayNetworkHandler {
    private boolean handlingChat = false;

    @Inject(method = "sendChat", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageHook(String content, CallbackInfo ci) {
        if (handlingChat) return;
        ChatEvent event = new ChatEvent(content);
        EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        } else if (!event.getMessage().equals(content)) {
            ci.cancel();
            handlingChat = true;
            ((ClientPacketListener) (Object) this).sendChat(event.getMessage());
            handlingChat = false;
        }
    }
}
