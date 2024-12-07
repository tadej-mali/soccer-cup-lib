package io.licitat.soccer;


import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestTeamFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeamTest {

    @Test
    public void givenNegativeId_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Team(-42, "Bad Data"));
    }

    @Test
    public void givenZeroId_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Team(0, "Bad Data"));
    }

    @Test
    public void givenNullName_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Team(42, null));
    }

    @Test
    public void givenEmptyName_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Team(42, ""));
    }

    @Test
    public void givenBlankName_toConstructor_shallAssert() {
        assertThrows(AssertionError.class, ()-> new Team(42, " \t"));
    }

    @Test
    public void givenValidData_toConstructor_isConstructed() {
        var theTeam = new Team(42, "Test team");

        assertEquals(42, theTeam.id());
        assertEquals("Test team", theTeam.name());
    }

    @Test
    public void givenTeamsWithSameId_isTheSameTeam_returnsTrue() {
        assertEquals(
            true,
            TestTeamFactory.getById(TeamId.Argentina).isTheSameTeam(TestTeamFactory.getById(TeamId.Argentina))
        );
    }

    @Test
    public void givenTeamsWithDifferentId_isTheSameTeam_returnsFalse() {
        assertEquals(
            false,
            TestTeamFactory.getById(TeamId.Argentina).isTheSameTeam(TestTeamFactory.getById(TeamId.Australia))
        );
    }
}
