package io.licitat.impl;

import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import org.junit.jupiter.api.Test;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.*;

public class MatchRepositoryImplTest {

    @Test
    public void givenNewMatch_addMatch_matchPersisted() {
        var repo = new MatchRepositoryImpl();
        var newMatch = buildNewMatch(TeamId.Brazil, TeamId.Italy);
        repo.AddMatch(newMatch);

        var persisted = repo.FindMatch(newMatch.getId());

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

        var persisted = repo.FindMatch(updatedMatch.getId());

        assertTrue(persisted.isPresent());
        assertEquals(updatedScore.homeTeamScore(), persisted.get().getHomeScore());
        assertEquals(updatedScore.awayTeamScore(), persisted.get().getAwayScore());
    }

    @Test
    public void givenMatchIsNotActive_updateMatch_shallAssert() {
        var repo = new MatchRepositoryImpl();
        var initialMatch = buildNewMatch(TeamId.Brazil, TeamId.Italy);

        repo.AddMatch(initialMatch);

        var updatedScore = new Score(
            initialMatch.getHomeTeam().id(), 1,
            initialMatch.getAwayTeam().id(), 3
        );

        repo.RemoveMatch(initialMatch);
        var updatedMatch = initialMatch.updateScore(updatedScore);

        assertThrows(AssertionError.class, () -> repo.UpdateMatch(updatedMatch));
    }

    @Test
    public void givenMatchInProgress_removeMatch_matchRemoved() {
        var repo = new MatchRepositoryImpl();
        var activeMatch = buildNewMatch(TeamId.Brazil, TeamId.Italy);
        repo.AddMatch(activeMatch);

        repo.RemoveMatch(activeMatch);

        var persisted = repo.FindMatch(activeMatch.getId());
        // One might argue that another match might be inserted in the meantime.
        // However, this is a unit test and such situation would not happen.
        assertTrue(persisted.isEmpty());
    }

    @Test
    public void givenMatchIsNotActive_removeMatch_shallAssert() {
        var repo = new MatchRepositoryImpl();
        var activeMatch = buildNewMatch(TeamId.Brazil, TeamId.Italy);

        repo.AddMatch(activeMatch);
        repo.RemoveMatch(activeMatch);

        var persisted = repo.FindMatch(activeMatch.getId());
        // One might argue that another match might be inserted in the meantime.
        // However, this is a unit test and such situation would not happen.
        assertTrue(persisted.isEmpty());
    }

    @Test
    public void givenMatchesInProgress_getAllMatches_shallReturnResult() {
        var repo = new MatchRepositoryImpl();
        var activeMatches = TestMatchFactory.buildMatches();
        activeMatches.forEach(repo::AddMatch);

        var persistedMatches = repo.GetAllMatches();
        // It really is a O(n^2) operation, but it is a small number of records
        var allMatchesFound = persistedMatches.stream()
            .allMatch(p -> activeMatches.stream()
                .anyMatch(a ->
                    a.getHomeTeam().isTheSameTeam(p.getHomeTeam())
                    && a.getAwayTeam().isTheSameTeam(p.getAwayTeam())
                ));

        assertTrue(allMatchesFound);
    }
}
