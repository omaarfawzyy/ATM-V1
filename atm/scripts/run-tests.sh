#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
EXTRA_CP=""

BUILD_DIR="$ROOT_DIR/build/test-classes"
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

# Compile production + test sources
find "$ROOT_DIR/src" "$ROOT_DIR/test" -name "*.java" > "$BUILD_DIR/sources.txt"

javac -cp "$EXTRA_CP" -d "$BUILD_DIR" @"$BUILD_DIR/sources.txt"

# Run tests (plain Java)
java -cp "$BUILD_DIR$EXTRA_CP" unit.UserDepositTest
java -cp "$BUILD_DIR$EXTRA_CP" unit.ATMPaperTest
java -cp "$BUILD_DIR$EXTRA_CP" integration.UserRepositorySaveTest
java -cp "$BUILD_DIR$EXTRA_CP" integration.ATMRepositoryLoadTest
