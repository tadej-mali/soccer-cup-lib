package io.licitat.impl;

import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.soccer.Scoreboard;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ScoreboardImpl implements Scoreboard {
    @Override
    public Match StartMatch(int homeTeamId, int awayTeamId) {
        throw new NotImplementedException();
    }

    @Override
    public Match UpdateScore(Score newScore) {
        throw new NotImplementedException();
    }

    @Override
    public void FinishMatch(Match activeMatch) {
        throw new NotImplementedException();
    }

    @Override
    public List<Match> GetActiveMatches() {
        throw new NotImplementedException();
    }
}
