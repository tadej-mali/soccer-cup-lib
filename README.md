# soccer-cup-lib

This scoreboard library is used for live tracking of Football World Cup matches and their scores.

# Data structures
## Team
A simple class describing a team participating in the match. The class instance are immutable, because they only
describe an actor of the system and have no internal state.

Each team is uniquely identified by its Id assigned by the backend. 

Since world cup is an international event, the name of the team needs to be localized (e.g. England, Anglija, Inghilterra, Angleterre...)
which is another rationale to use a machine generated unique identifier as a team Id.

## Match
A simple class describing a match between two teams.

Both teams must be specified and a team can not play against itself.

