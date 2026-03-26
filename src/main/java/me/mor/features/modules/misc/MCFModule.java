package me.mor.features.modules.misc;

import me.mor.MOR;
import me.mor.features.commands.Command;
import me.mor.features.modules.Module;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class MCFModule extends Module {
    private boolean pressed;

    public MCFModule() {
        super("MCF", "Middle click friend", Category.MISC);
    }

    @Override
    public void onTick() {
        if (GLFW.glfwGetMouseButton(mc.getWindow().handle(), 2) == 1) {
            if (!pressed) click();
            pressed = true;
        } else {
            pressed = false;
        }
    }

    private void click() {
        Entity targetedEntity = mc.crosshairPickEntity;
        if (!(targetedEntity instanceof Player)) return;
        String name = ((Player) targetedEntity).getGameProfile().name();

        if (MOR.friendManager.isFriend(name)) {
            MOR.friendManager.removeFriend(name);

            Command.sendMessage("{red} %s has been unfriended.", name);
        } else {
            MOR.friendManager.addFriend(name);
            Command.sendMessage("{aqua} %s has been friended.", name);
        }
    }
}
