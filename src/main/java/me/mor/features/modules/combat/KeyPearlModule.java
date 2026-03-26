package me.mor.features.modules.combat;

import me.mor.features.modules.Module;
import me.mor.features.settings.Setting;
import me.mor.util.inventory.InventoryUtil;
import me.mor.util.inventory.Result;
import net.minecraft.world.item.Items;

import static me.mor.util.inventory.InventoryUtil.FULL_SCOPE;
import static me.mor.util.inventory.InventoryUtil.HOTBAR_SCOPE;

public class KeyPearlModule extends Module {
    private final Setting<Boolean> inventory = bool("Inventory", false);

    public KeyPearlModule() {
        super("KeyPearl", "Throws a pearl when enabled.", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        disable();

        if (nullCheck()) return;

        Result result = InventoryUtil.find(Items.ENDER_PEARL, inventory.getValue() ? FULL_SCOPE : HOTBAR_SCOPE);
        InventoryUtil.withSwap(result, () -> mc.gameMode.useItem(mc.player, result.hand()));
    }
}
