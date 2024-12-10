package io.licitat.impl;

import io.licitat.data.EntityId;
import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class MatchRepositoryImpl implements MatchRepository {

    private final ConcurrentHashMap<String, Match> store = new ConcurrentHashMap<>();
    private final AtomicInteger lastMatchId = new AtomicInteger(0);
    private final ConcurrentHashMap<EntityId<Match>, Match> storeById = new ConcurrentHashMap<>();

    private String BuildKey (Match aMatch) {
        assert aMatch != null : "Match must not be null";

        return BuildKey(aMatch.getHomeTeam().id(), aMatch.getAwayTeam().id());
    }

    // 2^31 has 10 characters
    private final static int LONGEST_INT_LEN = 10;
    private final static String KEY_FORMAT = "%0" + LONGEST_INT_LEN + "d:%0" + LONGEST_INT_LEN + "d";

    private String BuildKey (int homeTeamId, int awayTeamId) {
        var min = Math.min(homeTeamId, awayTeamId);
        var max = Math.max(homeTeamId, awayTeamId);

        return String.format(KEY_FORMAT, min, max);
    }

    @Override
    public Optional<Match> FindMatch(EntityId<Match> matchId) {
        return Optional.ofNullable(storeById.get(matchId));
    }

    @Override
    public EntityId<Match> GetNextMatchId() {
        return EntityId.of(lastMatchId.incrementAndGet());
    }

    @Override
    public void AddMatch(Match aMatch, Supplier<Boolean> precondition) {
        synchronized (storeById) {
            assert precondition.get() : "The preconditions for creating new match are not fulfilled";

            storeById.compute(
                aMatch.getId(),
                (k, oldValue) -> {
                    assert oldValue == null : "A match with the same key already exists";
                    return aMatch;
                });
        }
    }

    @Override
    public void UpdateMatch(Match updatedMatch) {
        storeById.compute(
            updatedMatch.getId(),
            (k, oldValue) -> {
                assert oldValue != null : "A match with this key does not exist";
                return updatedMatch;
            });
    }

    @Override
    public void RemoveMatch(Match finishedMatch) {
        storeById.compute(
            finishedMatch.getId(),
            (k, oldValue) -> {
                assert oldValue != null : "A match with this key does not exist";
                return null;
            });
    }

    @Override
    public List<Match> GetAllMatches() {
        return storeById.values().stream().toList();
    }
}
