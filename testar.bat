@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -Xlint:all -d bin src\calculadora\*.java test\calculadora\*.java
if errorlevel 1 exit /b 1
java -cp bin calculadora.TesteCalculadora
exit /b %errorlevel%
