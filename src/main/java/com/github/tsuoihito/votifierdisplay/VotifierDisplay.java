package com.github.tsuoihito.votifierdisplay;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class VotifierDisplay extends JavaPlugin {

    private final DisplayConfig displayConfig = new DisplayConfig(this);
    private final Display display = new Display(this);
    private final VotifierManager votifierManager = new VotifierManager(this);

    @Override
    public void onEnable() {
        register();
        votifierManager.loadData();
        displayConfig.initConfig();
        display.initHologram();
    }

    @Override
    public void onDisable() {
        display.deleteAllHolograms();
        votifierManager.saveData();
    }

    private void register() {
        getCommand("votifierdisplay").setExecutor(this);
        getServer().getPluginManager().registerEvents(new VoteListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        sender.sendMessage(ChatColor.GRAY + "Setting display...");
        display.createHologram(player.getLocation());

        return true;

    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }

    public Display getDisplay() {
        return display;
    }

    public VotifierManager getVotifierManager() {
        return votifierManager;
    }
}
