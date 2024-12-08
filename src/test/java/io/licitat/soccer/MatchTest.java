package io.licitat.soccer;

import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {

    @Test
    public void givenTwoTeams_toConstructor_createNewMatch(){
        var theMatch = buildNewMatch(TeamId.Argentina, TeamId.Australia);

        // This violates the Law of Demeter, but let's keep it as it is because:
        //  - the Match and Team can be considered "frinds" - they are tightly connected in the domain
        //  - we are to develop a simple solution, let's not overengineer from start
        //  - we can refactor it later to theMatch.getHomeTeamId() if needed
        assertEquals(TeamId.Argentina.getValue(), theMatch.getHomeTeam().id());
        assertEquals(TeamId.Australia.getValue(), theMatch.getAwayTeam().id());

        assertEquals(0, theMatch.getHomeScore());
        assertEquals(0, theMatch.getAwayScore());
    }

    @Test
    public void givenNullHomeTeam_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Match(null, TestTeamFactory.getById(TeamId.Australia)));
    }

    @Test
    public void givenNullAwayTeam_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Match(TestTeamFactory.getById(TeamId.Australia), null));
    }

    @Test
    public void givenSameTeams_toConstructor_shallAssert() {
        assertThrows(
            AssertionError.class,
            ()-> new Match(
                TestTeamFactory.getById(TeamId.Australia),
                TestTeamFactory.getById(TeamId.Australia)
            ));
    }


    private Match buildNewMatch(TeamId homeTeamId, TeamId awayTeamId) {
        var homeTeam = TestTeamFactory.getById(homeTeamId);
        var awayTeam = TestTeamFactory.getById(awayTeamId);
        return new Match(homeTeam, awayTeam);
    }

    @Test
    public void givenNewScore_updateScore_shallReportNewScore() {

        var theMatch = buildNewMatch(TeamId.France, TeamId.Brazil);
        var newScore = new Score(
            theMatch.getHomeTeam().id(), 3,
            theMatch.getAwayTeam().id(), 2
        );

        var updatedMatch = theMatch.updateScore(newScore);
        var updatedScore = updatedMatch.getScore();

        assertEquals(newScore.homeTeamScore(), updatedScore.homeTeamScore());
        assertEquals(newScore.awayTeamScore(), updatedScore.awayTeamScore());
    }

    @Test
    public void givenInvalidTeamScored_updateScore_shallAssert() {

        var theMatch = buildNewMatch(TeamId.France, TeamId.Brazil);

        var invalidHomeTeamScore = new Score(
            TeamId.Mexico.getValue(), 3,
            theMatch.getAwayTeam().id(), 2
        );
        assertThrows(AssertionError.class, () -> theMatch.updateScore(invalidHomeTeamScore));

        var invalidAwayTeamScore = new Score(
            theMatch.getHomeTeam().id(), 3,
            TeamId.Uruguay.getValue(), 2
        );
        assertThrows(AssertionError.class, () -> theMatch.updateScore(invalidAwayTeamScore));

    }

    @Test
    public void givenInvalidScoreUpdate_updateScore_shallAssert() {

        Match currentMatch;
        {
            var theMatch = buildNewMatch(TeamId.France, TeamId.Brazil);
            var currentScore = new Score(
                    theMatch.getHomeTeam().id(), 3,
                    theMatch.getAwayTeam().id(), 2
            );
            currentMatch = theMatch.updateScore(currentScore);
        };


        var invalidHomeTeamScore = new Score(
            currentMatch.getHomeTeam().id(), 0,
            currentMatch.getAwayTeam().id(), 2
        );
        assertThrows(AssertionError.class, () -> currentMatch.updateScore(invalidHomeTeamScore));


        var invalidAwayTeamScore = new Score(
            currentMatch.getHomeTeam().id(), 3,
            currentMatch.getAwayTeam().id(), 0
        );
        assertThrows(AssertionError.class, () -> currentMatch.updateScore(invalidAwayTeamScore));
    }
}
