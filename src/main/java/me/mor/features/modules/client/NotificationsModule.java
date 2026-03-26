package me.mor.features.modules.client;

import me.mor.event.impl.ClientEvent;
import me.mor.event.system.Subscribe;
import me.mor.features.modules.Module;
import me.mor.features.settings.Setting;
import me.mor.util.TextUtil;
import me.mor.util.player.ChatUtil;

public class NotificationsModule extends Module {
    private static final String MODULE_FORMAT = "Toggled %s %s %s";

    public Setting<Boolean> moduleToggle = bool("Module Toggle", true);

    public NotificationsModule() {
        super("Notifications", "Displays notifications for various client events", Category.CLIENT);
    }

    @Subscribe
    public void onClient(ClientEvent event) {
        if (!moduleToggle.getValue()
                || event.getType() != ClientEvent.Type.TOGGLE_MODULE
                || event.getFeature() instanceof ClickGuiModule) {
            return;
        }

        boolean moduleState = event.getFeature().isEnabled();
        ChatUtil.sendMessage(TextUtil.text(MODULE_FORMAT,
                event.getFeature().getName(),
                moduleState ? "{green}" : "{red}",
                moduleState ? "on" : "off"), event.getFeature().getName());
    }
}
