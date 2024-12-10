package io.licitat.data;

import io.licitat.soccer.Match;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface MatchRepository {

    Optional<Match> FindMatch(EntityId<Match> matchId);

    EntityId<Match> GetNextMatchId();

    void AddMatch(Match aMatch, Supplier<Boolean> precondition);

    void UpdateMatch(Match upadatedMatch);

    void RemoveMatch(Match finishedMatch);

    List<Match> GetAllMatches();
}
