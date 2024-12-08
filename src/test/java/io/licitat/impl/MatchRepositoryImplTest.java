package io.licitat.impl;

import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import org.junit.jupiter.api.Test;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void givenMatchInProgress_addMatch_shallAssert() {
        var repo = new MatchRepositoryImpl();
        repo.AddMatch(buildNewMatch(TeamId.Brazil, TeamId.Italy));

        assertThrows(AssertionError.class, () -> repo.AddMatch(buildNewMatch(TeamId.Brazil, TeamId.Italy)));
    }

    @Test
    public void givenNewScore_updateMatch_matchUpdated() {
        var repo = new MatchRepositoryImpl();
        var initialMatch = buildNewMatch(TeamId.Brazil, TeamId.Italy);
        repo.AddMatch(initialMatch);

        var updatedScore = new Score(
            initialMatch.getHomeTeam().id(), 1,
            initialMatch.getAwayTeam().id(), 3
        );

        var updatedMatch = initialMatch.updateScore(updatedScore);

        repo.UpdateMatch(updatedMatch);

        var persisted = repo.FindMatch(TeamId.Brazil.getValue(), TeamId.Italy.getValue());

        assertTrue(persisted.isPresent());
        assertEquals(updatedScore.homeTeamScore(), persisted.get().getHomeScore());
        assertEquals(updatedScore.awayTeamScore(), persisted.get().getAwayScore());
    }
}
