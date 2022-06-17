package com.github.tsuoihito.votifierdisplay;

import org.bukkit.Location;

import java.util.Optional;

public class DisplayConfig {

    private final VotifierDisplay plugin;
    private Location displayLocation;

    public DisplayConfig(VotifierDisplay plugin) {
        this.plugin = plugin;
    }

    public void initConfig() {
        plugin.saveDefaultConfig();
        displayLocation = plugin.getConfig().getLocation("location");
    }

    private void saveConfig() {
        getDisplayLocation().ifPresent(location -> plugin.getConfig().set("location", location));
        plugin.saveConfig();
    }

    public Optional<Location> getDisplayLocation() {
        return Optional.ofNullable(displayLocation);
    }

    public void setDisplayLocation(Location displayLocation) {
        this.displayLocation = displayLocation;
        saveConfig();
    }
}
