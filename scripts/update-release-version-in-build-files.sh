#!/bin/bash
#
# Updates the version in all Gradle build files.
# Usage: scripts/update-release-version-in-build-files.sh <version>
#
set -euo pipefail

RELEASE_VERSION=${1:?Usage: $0 <version>}
RELEASE_VERSION_ESCAPED=${RELEASE_VERSION//./\\.}

# GNU sed (Linux / GitHub Actions) and BSD sed (macOS) disagree on -i.
sed_in_place() {
  if sed --version >/dev/null 2>&1; then
    sed -i "$@"
  else
    sed -i '' "$@"
  fi
}

set_version_in_file() {
  local file=$1
  local prefix=$2
  if ! grep -q -e "$prefix" "$file"; then
    echo "ERROR: pattern not found in $file: $prefix" >&2
    exit 1
  fi
  sed_in_place "s/$prefix[A-Z0-9.\-]*/$prefix$RELEASE_VERSION_ESCAPED/g" "$file"
  echo "Updated version in $file"
  grep --color -e "$prefix" "$file"
}

set_version_in_file "build.gradle" "def graphqlCodegenVersion = '"

set_version_in_file "plugins/gradle/graphql-java-codegen-gradle-plugin/build.gradle" "def graphqlCodegenGradlePluginVersion = '"

set_version_in_file "plugins/gradle/example-server/build.gradle" "io.github.kobylynskyi.graphql.codegen\" version \""

set_version_in_file "plugins/gradle/example-client/build.gradle" "implementation \"io.github.kobylynskyi:graphql-java-codegen:"
set_version_in_file "plugins/gradle/example-client/build.gradle" "io.github.kobylynskyi.graphql.codegen\" version \""

set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "id \"io.github.kobylynskyi.graphql.codegen\" version \""
set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "def graphqlCodegenClientKotlinVersion = '"
set_version_in_file "plugins/gradle/example-client-kotlin/build.gradle" "implementation \"io.github.kobylynskyi:graphql-java-codegen:"
