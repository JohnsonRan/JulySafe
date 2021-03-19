package com.github.julyss2019.bukkit.plugins.julysafe.config.lang;

import com.github.julyss2019.mcsp.julylibrary.message.JulyMessage;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LangHelper {
    public static void sendMsg(@NotNull CommandSender sender, @NotNull String msg) {
        JulyMessage.sendColoredMessage(sender, handleText(msg, null));
    }

    public static void sendMsg(@NotNull CommandSender sender, @NotNull String msg, @Nullable PlaceholderContainer placeholderContainer) {
        JulyMessage.sendColoredMessage(sender, handleText(msg, placeholderContainer));
    }

    public static void broadcastMsg(@NotNull String msg) {
        JulyMessage.broadcastColoredMessage(handleText(msg, null));
    }

    public static String handleText(@NotNull String text) {
        return handleText(text, null);
    }

    public static String handleText(@NotNull String text, @Nullable PlaceholderContainer placeholderContainer) {
        String result;

        result = JulyText.setPlaceholders(text, new PlaceholderContainer().add("prefix", Lang.getRootLangNode().getString("prefix")));

        if (placeholderContainer != null) {
            result = JulyText.setPlaceholders(result, placeholderContainer);
        }

        return JulyText.getColoredText(result);
    }
}
