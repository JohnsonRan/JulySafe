package com.github.julyss2019.bukkit.plugins.julysafe.config.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LangNode {
    private final String parentPath;

    public LangNode(@Nullable String parentPath) {
        this.parentPath = parentPath;
    }

    public String getString(@NotNull String path) {
        return Lang.getLangConfigSection().getString((parentPath == null ? "" : parentPath + ".") + path);
    }
}
