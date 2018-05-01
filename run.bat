@echo off
cls
prompt --
mkdir bin

:: parse cmd arguments
:: get filename or folder from the first argument
set FILENAME=%1


:: get settings file or use the default from the second argument
set SETTINGS=%2
IF "%2"=="" (
    set SETTINGS="project/src/UserSettings.json"
)

set PATTERNS=%3
IF "%3"=="" (
    set PATTERNS="project/patterns/Patterns.java"
)
@echo on


:: compile source code into bin/
javac -cp .;gson-2.8.2.jar;./bin/;spoon-core-6.1.0-jar-with-dependencies.jar -d bin project/src/main/*.java project/src/worker/*.java project/src/report/*.java project/src/util/*.java project/src/pattern_matcher/*.java
echo "Source code compiled"

:: run Main filename settings
:: usage: <filename|foldername> [<userSettings.json>]
java -cp .;gson-2.8.2.jar;./bin/;spoon-core-6.1.0-jar-with-dependencies.jar main.Main %FILENAME% "%SETTINGS%" "%PATTERNS%"