package io.licitat.impl;

import io.licitat.data.EntityId;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardImplIntegrationTest {

    private final Comparator<Match> displayOrder = new MatchSorter();

    @Test
    public void givenNewMatch_startMatch_newMatchCreated() {
        var board = new ScoreboardImpl(new MatchRepositoryImpl(), displayOrder);

        var newMatch = board.StartMatch(
            TestTeamFactory.getById(TeamId.Germany),
            TestTeamFactory.getById(TeamId.Argentina)
        );

        assertEquals(EntityId.of(TeamId.Germany.getValue()), newMatch.getHomeTeam().id());
        assertEquals(EntityId.of(TeamId.Argentina.getValue()), newMatch.getAwayTeam().id());
        assertEquals(0, newMatch.getHomeScore());
        assertEquals(0, newMatch.getAwayScore());
    }

    @Test
    public void givenNewScore_updateScore_scoreUpdated() {
        var board = new ScoreboardImpl(new MatchRepositoryImpl(), displayOrder);

        var currentMatch = board.StartMatch(
            TestTeamFactory.getById(TeamId.Uruguay),
            TestTeamFactory.getById(TeamId.Spain)
        );

        var newScore = new Score(
            currentMatch.getHomeTeam().id(), 2,
            currentMatch.getAwayTeam().id(), 1
        );
        var updatedMatch = board.UpdateScore(currentMatch.getId(), newScore);

        assertEquals(currentMatch.getHomeTeam().id(), updatedMatch.getHomeTeam().id());
        assertEquals(currentMatch.getAwayTeam().id(), updatedMatch.getAwayTeam().id());
        assertEquals(2, updatedMatch.getHomeScore());
        assertEquals(1, updatedMatch.getAwayScore());
    }

    @Test
    public void givenMatchOver_finishMatch_matchRemoved() {
        var board = new ScoreboardImpl(new MatchRepositoryImpl(), displayOrder);

        var currentMatch = board.StartMatch(
            TestTeamFactory.getById(TeamId.Uruguay),
            TestTeamFactory.getById(TeamId.Spain)
        );

        board.FinishMatch(currentMatch);

        var isNoLongerActive = board.GetActiveMatches().stream()
            .noneMatch(m ->
                m.getHomeTeam().isTheSameTeam(currentMatch.getHomeTeam())
                && m.getAwayTeam().isTheSameTeam(currentMatch.getAwayTeam())
            );

        assertTrue(isNoLongerActive);
    }

    @Test
    public void givenActiveMatches_getActiveMatches_getSortedListOfMatches() {
        var board = new ScoreboardImpl(new MatchRepositoryImpl(), displayOrder);

        var allMatches = TestMatchFactory.buildMatches();
        allMatches.forEach(m -> board.StartMatch(m.getHomeTeam(), m.getAwayTeam()));

        var orderedMatches = board.GetActiveMatches();
        assertEquals(allMatches.size(), orderedMatches.size());
    }
}