package io.licitat.soccer;

import java.util.List;

/**
 * This is the contract required to be implemented by the task
 */
public interface Scoreboard {

    Match StartMatch(int homeTeamId, int awayTeamId);

    Match UpdateScore(Score newScore);

    void FinishMatch(Match activeMatch);

    List<Match> GetActiveMatches();
}
