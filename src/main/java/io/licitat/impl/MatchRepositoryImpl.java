package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl implements MatchRepository {
    @Override
    public Optional<Match> FindMatch(int homeTeamId, int awayTeamId) {
        throw new NotImplementedException();
    }

    @Override
    public void AddMatch(Match aMatch) {
        throw new NotImplementedException();
    }

    @Override
    public void UpdateMatch(Match upadatedMatch) {
        throw new NotImplementedException();
    }

    @Override
    public void RemoveMatch(Match finishedMatch) {
        throw new NotImplementedException();
    }

    @Override
    public List<Match> GetAllMatches() {
        throw new NotImplementedException();
    }
}
