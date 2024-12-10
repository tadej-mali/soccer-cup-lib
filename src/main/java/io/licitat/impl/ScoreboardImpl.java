package io.licitat.impl;

import io.licitat.data.EntityId;
import io.licitat.data.MatchRepository;
import io.licitat.soccer.Match;
import io.licitat.soccer.Score;
import io.licitat.soccer.Scoreboard;
import io.licitat.soccer.Team;

import java.util.Comparator;
import java.util.List;

public class ScoreboardImpl implements Scoreboard {

    private final MatchRepository activeMatchesRepository;
    private final Comparator<Match> byDisplayOrder;

    public ScoreboardImpl(MatchRepository activeMatchesRepository, Comparator<Match> displayOrder) {
        this.activeMatchesRepository = activeMatchesRepository;
        this.byDisplayOrder = displayOrder;
    }

    private boolean isTeamEngaged(Match aMatch, Team aTeam) {
        return aMatch.getHomeTeam().isTheSameTeam(aTeam) || aMatch.getAwayTeam().isTheSameTeam(aTeam);
    }

    @Override
    public Match StartMatch(Team homeTeam, Team awayTeam) {

        var runningMatches = activeMatchesRepository.GetAllMatches();
        var engagement = runningMatches.stream()
            .filter(m -> isTeamEngaged(m, homeTeam) || isTeamEngaged(m, awayTeam))
            .findAny();

        assert engagement.isEmpty() : "One of the teams is engaged in another match";

        var newMatch = new Match(activeMatchesRepository.GetNextMatchId(), homeTeam, awayTeam);
        activeMatchesRepository.AddMatch(newMatch);

        return newMatch;
    }

    @Override
    public Match UpdateScore(EntityId<Match> matchToUpdateId, Score newScore) {

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
            .sorted(byDisplayOrder)
            .toList();
    }
}
