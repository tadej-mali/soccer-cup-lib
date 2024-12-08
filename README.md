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
The task specification says 
>Update score. This should receive a pair of absolute scores

For this reason I did not implement any complex rules (like score may change only for one point at a time) and
the behavior is quite relaxed. I only decided to forbid reducing the score to make it a bit
less trivial (but in reality one can imagine that an admin need to do this occasionally).

Additionally, the Match class is made immutable, because I want it to describe a match state i.e. a snapshot in time.
This should make the thread safe implementation a bit easier. However, I did not decide to rename the class at this time,
so that the feature like changes do not interfere with simple renamings in the git history. 

## Score
A new class that holds together Ids of both teams and their current score.

In real life, the class would be extended with timestamps and such in order to provide reliable 
history tracking and proper bookkeeping of match progress. For the sake of simplicity this is omitted for the
time being.

# Scoreboard
This is the class where the behavioral logic as specified is implemented.

It performs basic sanity checks, but the actual persistence is delegated to a repository, where also
concurrency issues shall be handled.