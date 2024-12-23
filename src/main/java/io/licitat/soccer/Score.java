package io.licitat.soccer;

import io.licitat.data.EntityId;

import java.util.Objects;

/**
 * A record describing a score update.
 */
public final class Score {
    private final EntityId<Team> homeTeamId;
    private final int homeTeamScore;
    private final EntityId<Team> awayTeamId;
    private final int awayTeamScore;


    public Score(EntityId<Team> homeTeamId, int homeTeamScore, EntityId<Team> awayTeamId, int awayTeamScore) {
        assert homeTeamId.value() > 0 : "Invalid home team Id";
        assert homeTeamScore >= 0 : "Invalid home score";
        assert awayTeamId.value() > 0 : "Invalid away team Id";
        assert awayTeamScore >= 0 : "Invalid away score";

        this.homeTeamId = homeTeamId;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamId = awayTeamId;
        this.awayTeamScore = awayTeamScore;
    }

    public Score(Team homeTeam, Team awayTeam) {

        assert homeTeam != null : "Home team can not be null";
        assert awayTeam != null : "Away team can not be null";

        homeTeamId = homeTeam.id();
        homeTeamScore = 0;
        awayTeamId = awayTeam.id();
        awayTeamScore = 0;
    }

    public Score update(Score newScore) {
        assert newScore.awayTeamId.equals(awayTeamId) : "Invalid away team";
        assert newScore.awayTeamScore >= awayTeamScore : "Invalid new away team score";

        assert newScore.homeTeamId.equals(homeTeamId) : "Invalid home team";
        assert newScore.homeTeamScore >= homeTeamScore : "Invalid new home team score";

        return new Score(
            newScore.homeTeamId, newScore.homeTeamScore,
            newScore.awayTeamId, newScore.awayTeamScore
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Score) obj;
        return this.homeTeamId.equals(that.homeTeamId) &&
                this.homeTeamScore == that.homeTeamScore &&
                this.awayTeamId.equals(that.awayTeamId) &&
                this.awayTeamScore == that.awayTeamScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeamId, homeTeamScore, awayTeamId, awayTeamScore);
    }

    @Override
    public String toString() {
        return "Score[" +
                "homeTeamId=" + homeTeamId + ", " +
                "homeTeamScore=" + homeTeamScore + ", " +
                "awayTeamId=" + awayTeamId + ", " +
                "awayTeamScore=" + awayTeamScore + ']';
    }

    public int homeTeamScore() {
        return homeTeamScore;
    }


    public int awayTeamScore() {
        return awayTeamScore;
    }

    public int totalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
