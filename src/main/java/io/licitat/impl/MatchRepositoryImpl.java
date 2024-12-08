package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MatchRepositoryImpl implements MatchRepository {

    private final ConcurrentHashMap<String, Match> store = new ConcurrentHashMap<>();

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
    public Optional<Match> FindMatch(int homeTeamId, int awayTeamId) {
        var matchKey = BuildKey(homeTeamId, awayTeamId);
        return Optional.ofNullable(store.get(matchKey));
    }

    @Override
    public void AddMatch(Match aMatch) {
        var matchKey = BuildKey(aMatch);

        store.compute(
            matchKey,
            (k, oldValue) -> {
                assert oldValue == null : "A match with the same key already exists";
                return aMatch;
            });
    }

    @Override
    public void UpdateMatch(Match updatedMatch) {

        var matchKey = BuildKey(updatedMatch);
        store.compute(
            matchKey,
            (k, oldValue) -> {
                assert oldValue != null : "A match with this key does not exist";
                return updatedMatch;
            });
    }

    @Override
    public void RemoveMatch(Match finishedMatch) {
        var matchKey = BuildKey(finishedMatch);
        store.compute(
            matchKey,
            (k, oldValue) -> {
                assert oldValue != null : "A match with this key does not exist";
                return null;
            });
    }

    @Override
    public List<Match> GetAllMatches() {
        return store.values().stream().toList();
    }
}
