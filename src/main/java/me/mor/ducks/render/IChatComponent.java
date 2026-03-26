package me.mor.ducks.render;

import net.minecraft.client.GuiMessage;
import net.minecraft.network.chat.MessageSignature;

public interface IChatComponent {
    void mor$addMessage(GuiMessage message);
    void mor$removeMessage(MessageSignature signature);
}