# IntelliJ Idea

## Running with Java 21
When running the tests the following warnings are displayed.
```
WARNING: A Java agent has been loaded dynamically (...\byte-buddy-agent-1.14.9.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
```
How to get rid of the warnings: see https://stackoverflow.com/a/79171147/1010666

**Note**: The warning appears when running tests in IDE.
When running tests with maven, the warning was removed in commit [1313646].
