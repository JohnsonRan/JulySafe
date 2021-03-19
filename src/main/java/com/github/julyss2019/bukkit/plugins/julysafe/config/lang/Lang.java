package com.github.julyss2019.bukkit.plugins.julysafe.config.lang;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Lang {
    private static ConfigurationSection langSection;

    public static void load() {
        langSection = YamlConfiguration.loadConfiguration(new File(JulySafe.getInstance().getDataFolder(), "config" + File.separator + "lang.yml"));
    }

    public static LangNode getRootLangNode() {
        return new LangNode(null);
    }

    public static LangNode getLangNode(@Nullable String path) {
        return new LangNode(path);
    }

    public static ConfigurationSection getLangConfigSection() {
        return langSection;
    }
}
