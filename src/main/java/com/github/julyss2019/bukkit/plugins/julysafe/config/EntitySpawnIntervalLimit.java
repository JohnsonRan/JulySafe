package com.github.julyss2019.bukkit.plugins.julysafe.config;

import com.github.julyss2019.bukkit.plugins.julysafe.target.EntityTarget;
import org.jetbrains.annotations.NotNull;

public class EntitySpawnIntervalLimit {
    private final EntityTarget target;
    private final int interval;

    public EntitySpawnIntervalLimit(@NotNull EntityTarget target, int interval) {
        this.target = target;
        this.interval = interval;
    }

    public EntityTarget getTarget() {
        return target;
    }

    public int getInterval() {
        return interval;
    }
}
