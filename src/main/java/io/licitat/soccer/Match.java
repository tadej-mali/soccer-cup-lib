package io.licitat.soccer;

import org.apache.commons.lang3.NotImplementedException;

/**
 * The Match class describes a current match between a home and away team and keeps its score.
 */
public class Match {

    private final Team homeTeam;
    private final Team awayTeam;
    private final int homeScore = 0;
    private final int awayScore = 0;

    /**
     * Create a new match, initial score is 0 : 0
     * @param homeTeam
     * @param awayTeam
     */
    public Match(Team homeTeam, Team awayTeam) {
        assert homeTeam != null : "Home team can not be null";
        assert awayTeam != null : "Away team can not be null";
        assert
            !homeTeam.isTheSameTeam(awayTeam)
            : String.format(
                "Home and away teams must be different. Trying to create a match between %s and %s",
                homeTeam.name(), awayTeam.name()
            );

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public Match updateScore(Score newScore) {
        throw new NotImplementedException();
    }

    public Score getScore() {
        throw new NotImplementedException();
    }
}
