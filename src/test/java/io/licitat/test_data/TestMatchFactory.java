package io.licitat.test_data;

import io.licitat.data.EntityId;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.licitat.test_data.TestTeamFactory.getById;
import static java.lang.Thread.sleep;

public class TestMatchFactory {

    private static AtomicInteger lastMatchUid = new AtomicInteger(0);

    public static EntityId<Match> getNextMatchId() {
        return EntityId.of(lastMatchUid.addAndGet(1));
    }
    public static Match buildNewMatch(TeamId homeId, TeamId awayId) {
        return buildMatch(homeId, 0, awayId, 0);
    }

    public static Match buildMatch(TeamId homeId, int homeScore, TeamId awayId, int awayScore) {
        return new Match(getNextMatchId(), getById(homeId), getById(awayId))
            .updateScore(
                new Score(
                    homeId.getValue(), homeScore,
                    awayId.getValue(), awayScore)
            );
    }

    public static List<Match> buildMatches() {

        var matches = new ArrayList<Match>();
        // Add a short sleep to ensure start times differ
        // It is ugly, but at this time we do not want to refactor the constructors
        try {
            matches.add(buildMatch(TeamId.Mexico, 0, TeamId.Canada, 5));
            sleep(10);
            matches.add(buildMatch(TeamId.Spain, 10, TeamId.Brazil, 2));
            sleep(10);
            matches.add(buildMatch(TeamId.Germany, 2, TeamId.France, 2));
            sleep(10);
            matches.add(buildMatch(TeamId.Uruguay, 6, TeamId.Italy, 6));
            sleep(10);
            matches.add(buildMatch(TeamId.Argentina, 3, TeamId.Australia, 1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return matches;
    }

    public static List<Match> sortedMatches() {

        var matches = new ArrayList<Match>();
        matches.add(buildMatch(TeamId.Uruguay, 6, TeamId.Italy, 6));
        matches.add(buildMatch(TeamId.Spain, 10, TeamId.Brazil, 2));
        matches.add(buildMatch(TeamId.Mexico, 0, TeamId.Canada, 5));
        matches.add(buildMatch(TeamId.Argentina, 3, TeamId.Australia, 1));
        matches.add(buildMatch(TeamId.Germany, 2, TeamId.France, 2));

        return matches;
    }
}
