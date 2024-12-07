package io.licitat.impl;

import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardImplTest {

    @Test
    public void givenNewMatch_startMatch_newMatchCreated() {

        var board = new ScoreboardImpl();

        var newMatch = board.StartMatch(TeamId.Germany.getValue(), TeamId.Argentina.getValue());

        assertEquals(newMatch.getHomeTeam().id(), TeamId.Germany.getValue());
        assertEquals(newMatch.getAwayTeam().id(), TeamId.Argentina.getValue());
        assertEquals(0, newMatch.getHomeScore());
        assertEquals(0, newMatch.getAwayScore());
    }

    @Test
    public void givenNewScore_updateScore_scoreUpdated() {

        var board = new ScoreboardImpl();
        var newScore = new Score(TeamId.Uruguay.getValue(), 2, TeamId.Spain.getValue(), 1);
        var updatedMatch = board.UpdateScore(newScore);


        assertEquals(TeamId.Uruguay.getValue(), updatedMatch.getHomeTeam().id());
        assertEquals(TeamId.Spain.getValue(), updatedMatch.getAwayTeam().id());
        assertEquals(2, updatedMatch.getHomeScore());
        assertEquals(1, updatedMatch.getAwayScore());
    }

    @Test
    public void givenMatchOver_FinishMatch_matchRemoved() {

        var board = new ScoreboardImpl();
        var newMatch = board.StartMatch(TeamId.Italy.getValue(), TeamId.Mexico.getValue());

        board.FinishMatch(newMatch);
    }

    @Test
    public void givenActiveMatches_getActiveMatches_getSortedListOfMatches() {

        var board = new ScoreboardImpl();

        var allMatches = board.GetActiveMatches();
    }
}
