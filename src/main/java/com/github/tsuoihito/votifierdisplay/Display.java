package com.github.tsuoihito.votifierdisplay;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Display {

    private final VotifierDisplay plugin;
    private Hologram hologram;

    public Display(VotifierDisplay plugin) {
        this.plugin = plugin;
    }

    public void initHologram() {
        if (plugin.getDisplayConfig().getDisplayLocation().isPresent()) {
            createHologram(plugin.getDisplayConfig().getDisplayLocation().get());
        }
    }

    public void createHologram(Location location) {

        deleteHologram();
        hologram = HologramsAPI.createHologram(plugin, location);
        setVoteData(hologram);
        plugin.getDisplayConfig().setDisplayLocation(location);

    }

    public void deleteHologram() {
        if (hologram != null) {
            hologram.delete();
        }
    }

    public void deleteAllHolograms() {
        HologramsAPI.getHolograms(plugin).forEach(Hologram::delete);
    }

    private void setVoteData(Hologram hologram) {

        Map<String, Integer> voteData = plugin.getVotifierManager().getVoteData();
        List<String> nameList = new ArrayList<>(voteData.keySet());

        nameList.sort((n1, n2) -> voteData.get(n2).compareTo(voteData.get(n1)));

        hologram.appendTextLine(ChatColor.YELLOW + "JMS投票数ランキング");

        nameList.forEach((name) -> {
            if (hologram.size() > 10) {
                return;
            }
            hologram.appendTextLine(ChatColor.GREEN + name + " " + ChatColor.AQUA + voteData.get(name).toString() + "票");
        });
    }

}
