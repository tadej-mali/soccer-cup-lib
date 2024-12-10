package io.licitat.soccer;

import io.licitat.data.EntityId;

import java.util.List;

/**
 * This is the contract required to be implemented by the task
 */
public interface Scoreboard {

    Match StartMatch(Team homeTeam, Team awayTeam);

    Match UpdateScore(EntityId<Match> matchToUpdateId, Score newScore);

    void FinishMatch(Match activeMatch);

    List<Match> GetActiveMatches();
}
