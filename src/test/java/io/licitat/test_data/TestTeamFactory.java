package io.licitat.test_data;

import io.licitat.data.EntityId;
import io.licitat.soccer.Team;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class TestTeamFactory {
    private static final Map<EntityId<Team>, Team> teamStore = new Hashtable<>();

    private static void add (TeamId id) {
        var theTeam = new Team(EntityId.of(id.getValue()), id.name());
        teamStore.put(theTeam.id(), theTeam);
    }

    static {
        Arrays
            .stream(TeamId.values())
            .forEach(TestTeamFactory::add);
    }

    public static Team getById(TeamId id) {
        return teamStore.get(EntityId.of(id.getValue()));
    }
}
