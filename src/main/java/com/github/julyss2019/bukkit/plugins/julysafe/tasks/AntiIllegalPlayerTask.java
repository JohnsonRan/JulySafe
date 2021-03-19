package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangNode;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class AntiIllegalPlayerTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final LangNode langNode = Lang.getLangNode("anti_illegal_player");
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp() && !mainConfig.getAntiIllegalPlayerOpWhitelist().contains(player.getName())) {
                player.setOp(false);
                kickAndBan(player);
                continue;
            }

            if (player.getGameMode() == GameMode.CREATIVE && !mainConfig.getAntiIllegalPlayerCreativeWhitelist().contains(player.getName())) {
                player.setGameMode(GameMode.SURVIVAL);
                kickAndBan(player);
            }
        }
    }

    private void kickAndBan(@NotNull Player player) {
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), LangHelper.handleText(langNode.getString("ban")), null, null);
        player.kickPlayer(LangHelper.handleText(langNode.getString("ban")));
        logger.info("[AntiIllegalPlayerTask] [封禁非法玩家] ID = " + player.getName() +  ", UUID = " + player.getUniqueId() + ".");
    }
}
