package io.licitat.test_data;

/**
 * The list of team IDs that will be used for testing
 */
public enum TeamId {

    Mexico(1),
    Canada(2),
    Spain(3),
    Brazil(4),
    Germany(5),
    France(6),
    Uruguay(7),
    Italy(8),
    Argentina(9),
    Australia(10);

    private final int value;

    /**
     * Constructor to associate an integer value with each enum constant
     */
    TeamId(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
