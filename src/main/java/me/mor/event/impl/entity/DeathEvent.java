package me.mor.event.impl.entity;

import me.mor.event.Event;
import net.minecraft.world.entity.LivingEntity;

public class DeathEvent extends Event {
    private final LivingEntity entity;

    public DeathEvent(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }
}
