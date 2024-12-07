package io.licitat.soccer;

import org.apache.commons.lang3.NotImplementedException;

/**
 * The Match class describes a current match between a home and away team and keeps its score.
 */
public class Match {

    private final Team homeTeam;
    private final Team awayTeam;
    private final int homeScore;
    private final int awayScore;

    /**
     * Create a new match, initial score is 0 : 0
     * @param homeTeam
     * @param awayTeam
     */
    public Match(Team homeTeam, Team awayTeam) {
         this (homeTeam, awayTeam, 0, 0);
    }

    /**
     * This constructor is only used to create a new instance with updated state
     * @param homeTeam
     * @param awayTeam
     * @param homeScore
     * @param awayScore
     */
    private Match(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        assert homeTeam != null : "Home team can not be null";
        assert awayTeam != null : "Away team can not be null";
        assert
                !homeTeam.isTheSameTeam(awayTeam)
                : String.format(
                "Home and away teams must be different. Trying to create a match between %s and %s",
                homeTeam.name(), awayTeam.name()
        );

        assert homeScore >= 0 : "Home score may not be negative";
        assert awayScore >= 0 : "Away score may not be negative";

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
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
        assert newScore.awayTeamId() == awayTeam.id() : "Invalid away team";
        assert newScore.awayTeamScore() >= awayScore : "Invalid new away team score";

        assert newScore.homeTeamId() == homeTeam.id() : "Invalid home team";
        assert newScore.homeTeamScore() >= homeScore : "Invalid new home team score";

        return new Match(homeTeam, awayTeam, newScore.homeTeamScore(), newScore.awayTeamScore());
    }

    public Score getScore() {
        return new Score(homeTeam.id(), homeScore, awayTeam.id(), awayScore);
    }
}
