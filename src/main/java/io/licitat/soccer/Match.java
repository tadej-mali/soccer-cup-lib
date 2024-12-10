package io.licitat.soccer;

import io.licitat.data.EntityId;

import java.time.Instant;
import java.util.Objects;

/**
 * The Match class describes a current match between a home and away team and keeps its score.
 */
public class Match {
    private final EntityId<Match> id;
    private final Instant startedAt;
    private final Team homeTeam;
    private final Team awayTeam;
    private final Score currentScore;

    /**
     * Create a new match, initial score is 0 : 0
     *
     * @param homeTeam
     * @param awayTeam
     */
    public Match(EntityId<Match> id, Team homeTeam, Team awayTeam) {
        this (id, Instant.now(), homeTeam, awayTeam, new Score(homeTeam, awayTeam));
    }

    /**
     * This constructor is only used to create a new instance with updated state
     *
     * @param id
     * @param startedAt
     * @param homeTeam
     * @param awayTeam
     * @param currentScore
     */
    private Match(EntityId<Match> id, Instant startedAt, Team homeTeam, Team awayTeam, Score currentScore) {
        assert id != null : "Id can not be null";
        assert homeTeam != null : "Home team can not be null";
        assert awayTeam != null : "Away team can not be null";
        assert
            !homeTeam.isTheSameTeam(awayTeam)
            : String.format(
                "Home and away teams must be different. Trying to create a match between %s and %s",
                homeTeam.name(), awayTeam.name()
            );

        this.id = id;
        this.startedAt = startedAt;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.currentScore = currentScore;
    }

    public Instant getStartedAt() {
        return startedAt;
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
        return new Match(id, startedAt, homeTeam, awayTeam, currentScore.update(newScore));
    }

    public Score getScore() {
        return currentScore;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Match match)) return false;
        return
            Objects.equals(startedAt, match.startedAt)
            && homeTeam.isTheSameTeam(match.homeTeam)
            && awayTeam.isTheSameTeam(match.awayTeam)
            && Objects.equals(currentScore, match.currentScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startedAt, homeTeam, awayTeam, currentScore);
    }
}
