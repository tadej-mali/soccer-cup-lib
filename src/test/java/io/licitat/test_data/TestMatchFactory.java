package io.licitat.test_data;

import io.licitat.soccer.Match;
import io.licitat.soccer.Score;

import java.util.ArrayList;
import java.util.List;

import static io.licitat.test_data.TestTeamFactory.getById;

public class TestMatchFactory {

    private static Match buildMatch(TeamId homeId, int homeScore, TeamId awayId, int awayScore) {
        return new Match(getById(homeId), getById(awayId))
            .updateScore(
                new Score(
                    homeId.getValue(), homeScore,
                    awayId.getValue(), awayScore)
            );
    }

    public static List<Match> buildMatches() {

        var matches = new ArrayList<Match>();
        matches.add(buildMatch(TeamId.Mexico, 0, TeamId.Canada, 5));
        matches.add(buildMatch(TeamId.Spain, 10, TeamId.Brazil, 2));
        matches.add(buildMatch(TeamId.Germany, 2, TeamId.France, 2));
        matches.add(buildMatch(TeamId.Uruguay, 6, TeamId.Italy, 6));
        matches.add(buildMatch(TeamId.Argentina, 3, TeamId.Australia, 1));

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
