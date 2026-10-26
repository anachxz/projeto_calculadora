#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -Xlint:all -d bin src/calculadora/*.java test/calculadora/*.java
java -cp bin calculadora.TesteCalculadora
