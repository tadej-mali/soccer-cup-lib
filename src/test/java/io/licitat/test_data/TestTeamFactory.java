package io.licitat.test_data;

import io.licitat.soccer.Team;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class TestTeamFactory {
    private static final Dictionary<Integer, Team> teamStore = new Hashtable<>();

    private static void add (TeamId id) {
        var theTeam = new Team(id.getValue(), id.name());
        teamStore.put(theTeam.id(), theTeam);
    }

    static {
        Arrays
            .stream(TeamId.values())
            .forEach(TestTeamFactory::add);
    }

    public static Team getById(TeamId id) {
        return teamStore.get(id.getValue());
    }
}
