package io.licitat.impl;

import io.licitat.soccer.Match;
import io.licitat.test_data.TeamId;
import io.licitat.test_data.TestMatchFactory;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchSorterTest {

    @Test
    public void givenDifferentTotalScore_compare_ChooseHigherTotalScore() {
        var theSorter = new MatchSorter();

        var totalScore_9 = TestMatchFactory.buildMatch(TeamId.Brazil, 5, TeamId.Argentina, 4);
        var totalScore_5 = TestMatchFactory.buildMatch(TeamId.Italy, 2, TeamId.Spain, 3);

        assertTrue(theSorter.compare(totalScore_5, totalScore_9) > 0);
        assertTrue(theSorter.compare(totalScore_9, totalScore_5) < 0);
    }

    @Test
    public void givenEqualTotalScore_compare_ChooseMostRecent() throws InterruptedException {
        var theSorter = new MatchSorter();

        var totalScore_5_early = TestMatchFactory.buildMatch(TeamId.Brazil, 3, TeamId.Argentina, 2);
        sleep(10);
        var totalScore_5_late = TestMatchFactory.buildMatch(TeamId.Italy, 2, TeamId.Spain, 3);

        assertTrue(theSorter.compare(totalScore_5_early, totalScore_5_late) > 0);
        assertTrue(theSorter.compare(totalScore_5_late, totalScore_5_early) < 0);
    }

    @Test
    public void givenListOfMatches_sort_CorrectlySorted() {
        var theSorter = new MatchSorter();
        var allMatches = TestMatchFactory.buildMatches().stream().sorted(theSorter).toList();

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
