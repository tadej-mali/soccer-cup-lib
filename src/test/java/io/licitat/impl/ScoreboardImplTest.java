package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.soccer.Team;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.Optional;

import static io.licitat.test_data.TestMatchFactory.buildNewMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        var orderMock = (Comparator<Match>) mock(Comparator.class);
        var board = new ScoreboardImpl(repositoryMock, orderMock);

        var allMatches = TestMatchFactory.buildMatches();
        when(repositoryMock.GetAllMatches()).thenReturn(allMatches);

        var orderedMatches = board.GetActiveMatches();
        assertEquals(allMatches.size(), orderedMatches.size());
        verify(orderMock, atLeastOnce()).compare(any(), any());
    }

    @Test
    public void givenTeamAlreadyEngaged_startNewMatch_shallAssert() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock, displayOrder);

        var allMatches = TestMatchFactory.buildMatches();
        when(repositoryMock.GetAllMatches()).thenReturn(allMatches);

        var anActiveMatch = allMatches.getFirst();
        var engagedTeam_1 = anActiveMatch.getHomeTeam();
        var engagedTeam_2 = anActiveMatch.getHomeTeam();
        var freeTeam_1 = TestTeamFactory.getById(TeamId.Slovenia);
        var freeTeam_2 = TestTeamFactory.getById(TeamId.Portugal);

        assertThrows(AssertionError.class, () -> board.StartMatch(engagedTeam_1, freeTeam_1));
        assertThrows(AssertionError.class, () -> board.StartMatch(freeTeam_2, engagedTeam_1));
        assertThrows(AssertionError.class, () -> board.StartMatch(engagedTeam_2, freeTeam_2));
        assertThrows(AssertionError.class, () -> board.StartMatch(freeTeam_1, engagedTeam_2));

        assertThrows(AssertionError.class, () -> board.StartMatch(engagedTeam_2, engagedTeam_1));
    }

    @Test
    public void givenNoSuchMatch_updateScore_shallThrow() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock, displayOrder);

        var allMatches = TestMatchFactory.buildMatches();
        when(repositoryMock.GetAllMatches()).thenReturn(allMatches);

        assertThrows(
            RuntimeException.class,
            () -> board.UpdateScore(
                    new Score(
                    TeamId.Slovenia.getValue(), 3,
                    TeamId.Portugal.getValue(), 1)
            ));
    }

    @Test
    public void givenNoSuchMatch_finishMatch_shallThrow() {
        var repositoryMock = Mockito.mock(MatchRepository.class);
        var board = new ScoreboardImpl(repositoryMock, displayOrder);

        var allMatches = TestMatchFactory.buildMatches();
        when(repositoryMock.GetAllMatches()).thenReturn(allMatches);

        assertThrows(
            RuntimeException.class,
            () -> board.FinishMatch(TestMatchFactory.buildNewMatch(TeamId.Portugal, TeamId.Slovenia))
        );
    }
}