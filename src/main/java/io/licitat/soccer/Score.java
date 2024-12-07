package io.licitat.soccer;

/**
 * A record describing a score update.
 * @param homeTeamId
 * @param homeTeamScore
 * @param awayTeamId
 * @param awayTeamScore
 */
public record Score(int homeTeamId, int homeTeamScore, int awayTeamId, int awayTeamScore) {

    public Score {
        assert homeTeamId > 0 : "Invalid home team Id";
        assert homeTeamScore >= 0 : "Invalid home score";
        assert awayTeamId  > 0 : "Invalid away team Id";
        assert awayTeamScore >= 0 : "Invalid away score";
    }
}
