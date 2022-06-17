package com.github.tsuoihito.votifierdisplay;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {

    private final VotifierDisplay plugin;

    public VoteListener(VotifierDisplay plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifier(VotifierEvent event) {

        plugin.getVotifierManager().getVotes().add(event.getVote());
        plugin.getDisplay().initHologram();

    }
}
