package com.github.julyss2019.bukkit.plugins.julysafe.tasks;

import com.github.julyss2019.bukkit.plugins.julysafe.JulySafe;
import com.github.julyss2019.bukkit.plugins.julysafe.bossbar.GlobalBossBarManager;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfig;
import com.github.julyss2019.bukkit.plugins.julysafe.config.MainConfigHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.Lang;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangHelper;
import com.github.julyss2019.bukkit.plugins.julysafe.config.lang.LangNode;
import com.github.julyss2019.mcsp.julylibrary.logger.Logger;
import com.github.julyss2019.mcsp.julylibrary.message.JulyMessage;
import com.github.julyss2019.mcsp.julylibrary.text.JulyText;
import com.github.julyss2019.mcsp.julylibrary.text.PlaceholderContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

public class CleanEntityTask extends BukkitRunnable {
    private final JulySafe plugin = JulySafe.getInstance();
    private final GlobalBossBarManager globalBossBarManager = plugin.getGlobalBossBarManager();
    private final Logger logger = plugin.getPluginLogger();
    private final MainConfig mainConfig = plugin.getMainConfig();
    private final MainConfigHelper mainConfigHelper = plugin.getMainConfigHelper();
    private final LangNode langNode = Lang.getLangNode("clean_entity");
    private final BossBar bossBar = Bukkit.createBossBar("", mainConfig.getCleanEntityBossBarColor(), mainConfig.getCleanEntityBossBarStyle());
    private int counter = 0;

    @Override
    public void run() {
        counter++;

        int countdown = getCountdown();

        if (countdown == 0) {
            int total = 0;

            for (World world : Bukkit.getWorlds()) {
                if (!mainConfigHelper.isCleanDropWorld(world)) {
                    continue;
                }

                for (Entity entity : world.getEntities()) {

                    if (mainConfig.getCleanEntityTarget().isTarget(entity)) {
                        total += 1;
                        logger.debug("[clean_entity] [删除实体] 实体 = " + entity.getType().name() + ", 位置 = " + entity.getLocation() + ".");
                        entity.remove();
                    }
                }
            }

            JulyMessage.broadcastColoredMessage(JulyText.setPlaceholders(langNode.getString("finished"), new PlaceholderContainer()
                    .add("count", String.valueOf(total))));
            counter = 0;
            globalBossBarManager.unregisterGlobalBar(bossBar);
            bossBar.removeAll();
            return;
        }

        if (mainConfig.getCleanEntityCountdownSecond() >= countdown) {
            if (!globalBossBarManager.hasGlobalBar(bossBar)) {
                globalBossBarManager.registerGlobalBar(bossBar);
            }

            bossBar.setTitle(JulyText.getColoredText(LangHelper.handleText(langNode.getString("countdown_boss_bar"),
                    new PlaceholderContainer()
                            .add("seconds", String.valueOf(countdown)))));
            bossBar.setProgress((double) (getCountdown()) / mainConfig.getCleanEntityCountdownSecond());
        }
    }

    public void onDisabled() {
        bossBar.removeAll();
    }

    public int getCountdown() {
        return mainConfig.getCleanEntityInterval() - counter;
    }
}
