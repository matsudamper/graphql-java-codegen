#!/usr/bin/env bash
# Cursor Cloud Agent の install。コンソールはこのスクリプトを実行するだけにする。
set -euo pipefail

export VERSION_POSTFIX=-SNAPSHOT

./gradlew --no-daemon assemble publishToMavenLocal
./gradlew --no-daemon -p plugins/gradle/graphql-java-codegen-gradle-plugin assemble publishToMavenLocal
