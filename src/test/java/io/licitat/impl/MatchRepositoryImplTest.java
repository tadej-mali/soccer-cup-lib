package io.licitat.impl;

import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import org.junit.jupiter.api.Test;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchRepositoryImplTest {

    @Test
    public void givenNewMatch_addMatch_matchPersisted() {
        var repo = new MatchRepositoryImpl();
        repo.AddMatch(buildNewMatch(TeamId.Brazil, TeamId.Italy));

        var persisted = repo.FindMatch(TeamId.Brazil.getValue(), TeamId.Italy.getValue());

        assertTrue(persisted.isPresent());
        assertEquals(TeamId.Brazil.getValue(), persisted.get().getHomeTeam().id());
        assertEquals(TeamId.Italy.getValue(), persisted.get().getAwayTeam().id());
    }
}
