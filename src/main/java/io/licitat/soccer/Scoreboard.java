package io.licitat.soccer;

import java.util.List;

/**
 * This is the contract required to be implemented by the task
 */
public interface Scoreboard {

    Match StartMatch(Team homeTeam, Team awayTeam);

    Match UpdateScore(Score newScore);

    void FinishMatch(Match activeMatch);

    List<Match> GetActiveMatches();
}
