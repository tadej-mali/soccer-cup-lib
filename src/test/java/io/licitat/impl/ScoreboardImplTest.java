package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.Optional;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ScoreboardImplTest {

    private final Comparator<Match> displayOrder = new MatchSorter();
    @Test
    public void givenNewMatch_startMatch_newMatchCreated() {

        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock, displayOrder);

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
        var board = new ScoreboardImpl(repositoryMock, displayOrder);
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
        var board = new ScoreboardImpl(repositoryMock, displayOrder);
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
        @SuppressWarnings("unchecked")
        var orderMock = (Comparator<Match>)mock(Comparator.class);
        var board = new ScoreboardImpl(repositoryMock, orderMock);

        var allMatches = TestMatchFactory.buildMatches();
        when(repositoryMock.GetAllMatches()).thenReturn(allMatches);

        var orderedMatches = board.GetActiveMatches();
        assertEquals(allMatches.size(), orderedMatches.size());
        verify(orderMock, atLeastOnce()).compare(any(), any());
    }
}
