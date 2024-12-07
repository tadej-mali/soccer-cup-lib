package io.licitat.soccer;

/**
 * The Match class describes a current match between a home and away team and keeps its score.
 */
public class Match {

    private final Team homeTeam;
    private final Team awayTeam;
    private final Score currentScore;

    /**
     * Create a new match, initial score is 0 : 0
     * @param homeTeam
     * @param awayTeam
     */
    public Match(Team homeTeam, Team awayTeam) {
        this (homeTeam, awayTeam, new Score(homeTeam, awayTeam));
    }

    /**
     * This constructor is only used to create a new instance with updated state
     * @param homeTeam
     * @param awayTeam
     * @param currentScore
     */
    private Match(Team homeTeam, Team awayTeam, Score currentScore) {
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
        this.currentScore = currentScore;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return currentScore.homeTeamScore();
    }

    public int getAwayScore() {
        return currentScore.awayTeamScore();
    }

    public Match updateScore(Score newScore) {
        return new Match(homeTeam, awayTeam, currentScore.update(newScore));
    }

    public Score getScore() {
        return currentScore;
    }
}
