#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
javadoc -quiet -Xdoclint:all -private -author -version -encoding UTF-8 -docencoding UTF-8 -charset UTF-8 -d docs src/calculadora/*.java test/calculadora/*.java
