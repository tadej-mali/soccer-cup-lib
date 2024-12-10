package io.licitat.data;

import io.licitat.soccer.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    Optional<Match> FindMatch(int homeTeamId, int awayTeamId);

    EntityId<Match> GetNextMatchId();

    void AddMatch(Match aMatch);

    void UpdateMatch(Match upadatedMatch);

    void RemoveMatch(Match finishedMatch);

    List<Match> GetAllMatches();
}
