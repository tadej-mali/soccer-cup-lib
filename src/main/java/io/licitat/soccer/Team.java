package io.licitat.soccer;

import org.apache.commons.lang3.StringUtils;

/**
 * The Team class describes a soccer team participating in a match.
 * Each team is identified with a unique identifier.
 * Besides its Id it also contains a default team name.
 * Note: team name can be localized (e.g. when a team name equals a country name)
 */
public class Team {
    private final int id;
    private final String name;

    /**
     * Constructor
     * @param id A non-negative unique identifier of a team
     * @param name A human-readable string identifying the team.
     *             It is not localizable and shall not be used for display purposes.
     *             It is primarily intended to produce human-readable diagnostic traces
     */
    public Team(int id, String name) {
        assert id > 0 : "Invalid team Id";
        assert StringUtils.isNotBlank(name) : "Invalid team display name";

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
