package com.github.tsuoihito.votifierdisplay;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vexsoftware.votifier.model.Vote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class VotifierManager {

    private final VotifierDisplay plugin;
    private List<Vote> votes;
    private final ObjectMapper mapper;
    private final File jsonFile;

    public VotifierManager(VotifierDisplay plugin) {
        this.plugin = plugin;
        votes = new ArrayList<>();
        mapper = new ObjectMapper();
        jsonFile = new File(plugin.getDataFolder(), "votes.json");
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Map<String, Integer> getVoteData() {

        Map<String, Integer> voteData = new HashMap<>();

        votes.stream().map(Vote::getUsername).distinct().forEach(name ->
                voteData.put(name, (int) votes.stream().filter(vote -> vote.getUsername().equals(name)).count())
        );

        return voteData;
    }

    public void loadData() {

        try {
            if (jsonFile.createNewFile()) {
                saveData();
            }
            votes = mapper.readValue(jsonFile, new TypeReference<List<Vote>>() {});
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, e.toString());
        }

    }

    public void saveData() {

        try {
            mapper.writeValue(jsonFile, votes);
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, e.toString());
        }

    }
}
