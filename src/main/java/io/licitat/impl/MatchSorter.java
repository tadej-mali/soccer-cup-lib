package io.licitat.impl;

import io.licitat.soccer.Match;

import java.util.Comparator;

public class MatchSorter implements Comparator<Match> {
    @Override
    public int compare(Match m1, Match m2) {
        // The match with higher total score shall be at the top
        if (m1.getScore().totalScore() > m2.getScore().totalScore()) { return -1; }
        if (m1.getScore().totalScore() < m2.getScore().totalScore()) { return +1; }

        // The match that started earlier shall be at the top
        return -m1.getStartedAt().compareTo(m2.getStartedAt());
    }
}
