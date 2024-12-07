package io.licitat.data;

import io.licitat.soccer.Team;

import java.util.Locale;

public interface TeamRepository {
    String translate(Locale displayLocale, int TeamId);
}
