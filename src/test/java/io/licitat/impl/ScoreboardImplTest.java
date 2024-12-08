package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScoreboardImplTest {

    @Test
    public void givenNewMatch_startMatch_newMatchCreated() {

        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock);

        var newMatch = board.StartMatch(
            TestTeamFactory.getById(TeamId.Germany),
            TestTeamFactory.getById(TeamId.Argentina)
        );

        assertEquals(newMatch.getHomeTeam().id(), TeamId.Germany.getValue());
        assertEquals(newMatch.getAwayTeam().id(), TeamId.Argentina.getValue());
        assertEquals(0, newMatch.getHomeScore());
        assertEquals(0, newMatch.getAwayScore());

        verify(repositoryMock).AddMatch(newMatch);
    }

    @Test
    public void givenNewScore_updateScore_scoreUpdated() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock);
        var currentMatch = buildNewMatch(TeamId.Uruguay, TeamId.Spain);

        when(repositoryMock.FindMatch(
                currentMatch.getHomeTeam().id(), currentMatch.getAwayTeam().id()
            ))
        .thenReturn(Optional.of(currentMatch));

        var newScore = new Score(
            currentMatch.getHomeTeam().id(), 2,
            currentMatch.getAwayTeam().id(), 1
        );
        var updatedMatch = board.UpdateScore(newScore);

        verify(repositoryMock).UpdateMatch(updatedMatch);
        assertEquals(currentMatch.getHomeTeam().id(), updatedMatch.getHomeTeam().id());
        assertEquals(currentMatch.getAwayTeam().id(), updatedMatch.getAwayTeam().id());
        assertEquals(2, updatedMatch.getHomeScore());
        assertEquals(1, updatedMatch.getAwayScore());
    }

    @Test
    public void givenMatchOver_FinishMatch_matchRemoved() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock);
        var currentMatch = buildNewMatch(TeamId.Uruguay, TeamId.Spain);

        when(repositoryMock.FindMatch(
            currentMatch.getHomeTeam().id(), currentMatch.getAwayTeam().id()
        ))
        .thenReturn(Optional.of(currentMatch));

        board.FinishMatch(currentMatch);

        verify(repositoryMock).RemoveMatch(currentMatch);
    }

    @Test
    public void givenActiveMatches_getActiveMatches_getSortedListOfMatches() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock);

        when(repositoryMock.GetAllMatches()).thenReturn(TestMatchFactory.buildMatches());

        var allMatches = board.GetActiveMatches();
        var expectedMatches = TestMatchFactory.sortedMatches();

        for (int i = 0; i < allMatches.size(); i++) {
            var expected = expectedMatches.get(i);
            var actual = allMatches.get(i);
            assertTrue(expected.getHomeTeam().isTheSameTeam(actual.getHomeTeam()));
            assertTrue(expected.getAwayTeam().isTheSameTeam(actual.getAwayTeam()));
            assertEquals(expected.getHomeScore(), actual.getHomeScore());
            assertEquals(expected.getAwayScore(), actual.getAwayScore());
        }
    }
}
