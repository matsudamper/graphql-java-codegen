# Release process (fork)

This fork publishes to **GitHub Packages of `matsudamper/graphql-java-codegen`**
(`https://maven.pkg.github.com/matsudamper/graphql-java-codegen`).
It does *not* publish to Maven Central or to the Gradle Plugin Portal.

Published artifacts:

| Artifact | Coordinates |
|---|---|
| Library | `io.github.kobylynskyi:graphql-java-codegen` |
| Gradle plugin | `io.github.kobylynskyi:graphql-java-codegen-gradle-plugin` (+ plugin marker `io.github.kobylynskyi.graphql.codegen`) |

## Snapshots

Nothing to do — every push to `main` runs
[`release-snapshot.yml`](.github/workflows/release-snapshot.yml) and publishes
`<version>-SNAPSHOT-<yyyyMMdd-HHmmss>`. Running that workflow manually publishes
`<version>-BUILD-<commit sha>` instead.

## Releasing a version

1. Run the **Release** workflow (`Actions` → `Release` → `Run workflow`) with:
   - `release_version`: the version to release, e.g. `5.11.0`
   - `release_branch`: usually `main`
   - `publish_gradle_plugin`: `Y` unless only the library should be published
2. The workflow then:
   1. rewrites the version in all build files and READMEs
      (`scripts/update-release-version-in-*.sh`),
   2. builds the library, the Gradle plugin and the three example projects,
   3. commits `Bump version to <version>` and creates the tag `v<version>`,
   4. publishes the library and the plugin to GitHub Packages,
   5. pushes the commit and the tag, and creates a GitHub Release with
      auto-generated notes.
3. Verify the result on the
   [Packages page](https://github.com/matsudamper/graphql-java-codegen/packages).

No secrets need to be configured: the built-in `GITHUB_TOKEN` is used
(`permissions: contents: write, packages: write`).

## Bumping the version without releasing

Run the **Update Version** workflow to only rewrite the version in the build
files and push the bump commit (e.g. to move on to the next development
version).

## Consuming the artifacts

GitHub Packages requires authentication even for public packages, so consumers
need a personal access token with the `read:packages` scope:

```groovy
repositories {
    maven {
        url "https://maven.pkg.github.com/matsudamper/graphql-java-codegen"
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
```

For the Gradle plugin, declare the same repository inside `pluginManagement`
in `settings.gradle` — see [plugins/gradle/README.md](plugins/gradle/README.md).
