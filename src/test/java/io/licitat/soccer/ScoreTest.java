package io.licitat.soccer;

import io.licitat.data.EntityId;
import io.licitat.test_data.TeamId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    public void givenTeamScores_totalScore_addsUpScores() {
        var theScore = new Score(
            EntityId.of(TeamId.Argentina.getValue()), 3,
            EntityId.of(TeamId.Australia.getValue()), 2);

        assertEquals(5, theScore.totalScore());
    }
}
