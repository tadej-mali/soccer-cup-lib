package io.licitat.soccer;

import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {

    @Test
    public void givenTwoTeams_toConstructor_createNewMatch(){

        var homeTeam = TestTeamFactory.getById(TeamId.Argentina);
        var awayTeam = TestTeamFactory.getById(TeamId.Australia);
        var theMatch = new Match(homeTeam, awayTeam);

        // This violates the Law of Demeter, but let's keep it as it is because:
        //  - the Match and Team can be considered "frinds" - they are tightly connected in the domain
        //  - we are to develop a simple solution, let's not overengineer from start
        //  - we can refactor it later to theMatch.getHomeTeamId() if needed
        assertEquals(homeTeam.id(), theMatch.getHomeTeam().id());
        assertEquals(awayTeam.id(), theMatch.getHomeTeam().id());

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
}
