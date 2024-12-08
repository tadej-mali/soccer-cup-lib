package io.licitat.impl;

import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.soccer.Scoreboard;
import io.licitat.soccer.Team;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public class ScoreboardImpl implements Scoreboard {

    private final MatchRepository activeMatchesRepository;

    public ScoreboardImpl(MatchRepository activeMatchesRepository) {
        this.activeMatchesRepository = activeMatchesRepository;
    }

    @Override
    public Match StartMatch(Team homeTeam, Team awayTeam) {

        var newMatch = new Match(homeTeam, awayTeam);
        activeMatchesRepository.AddMatch(newMatch);

        return newMatch;
    }

    @Override
    public Match UpdateScore(Score newScore) {

        var theMatch = activeMatchesRepository
            .FindMatch(newScore.homeTeamId(), newScore.awayTeamId())
            .orElseThrow(() -> new RuntimeException("No such match in progress"));

        var updatedMatch = theMatch.updateScore(newScore);
        activeMatchesRepository.UpdateMatch(updatedMatch);
        return updatedMatch;
    }

    @Override
    public void FinishMatch(Match activeMatch) {
        var theMatch = activeMatchesRepository
            .FindMatch(activeMatch.getHomeTeam().id(), activeMatch.getAwayTeam().id())
            .orElseThrow(() -> new RuntimeException("No such match in progress"));

        activeMatchesRepository.RemoveMatch(theMatch);
    }

    @Override
    public List<Match> GetActiveMatches() {
        var runningMatches = activeMatchesRepository.GetAllMatches();

        return runningMatches
            .stream()
            .sorted((m1, m2) -> {
                if (m1.getScore().totalScore() > m2.getScore().totalScore()) { return -1; }
                if (m1.getScore().totalScore() < m2.getScore().totalScore()) { return +1; }

                return -m1.getStartedAt().compareTo(m2.getStartedAt());
            })
            .toList();
    }
}
