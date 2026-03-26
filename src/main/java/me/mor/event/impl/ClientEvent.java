package me.mor.event.impl;

import me.mor.event.Event;
import me.mor.features.Feature;
import me.mor.features.settings.Setting;

public class ClientEvent extends Event {
    private final Type type;
    private final Feature feature;
    private Setting<?> setting;

    public ClientEvent(Type type, Feature feature) {
        this.type = type;
        this.feature = feature;
    }

    public ClientEvent(Setting<?> setting) {
        this(Type.SETTING_UPDATE, setting.getFeature());
        this.setting = setting;
    }

    public Type getType() {
        return type;
    }

    public Feature getFeature() {
        return this.feature;
    }

    public Setting<?> getSetting() {
        return this.setting;
    }

    public enum Type {
        TOGGLE_MODULE,
        SETTING_UPDATE
    }
}
