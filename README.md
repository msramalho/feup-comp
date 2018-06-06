# Project Title: Multipurpose Pattern Matcher

## Group: G41

NAME1: André Miguel Ferreira da Cruz, NR1: 201503776, GRADE1: 19, CONTRIBUTION1: 33,33%
NAME2: Edgar Filipe Gomes Carneiro, NR2: 201503776, GRADE2: 19, CONTRIBUTION2: 33,33%
NAME3: Miguel Sozinho Ramalho, NR3: 201403027, GRADE3: 19, CONTRIBUTION3: 33,33%

### GLOBAL Grade of the project: 19

## Summary

This Project is a multipurpose pattern matching tool, enabling you to analyse whole projects for specific code patterns (_e.g._ use of ternary operators instead of if-else constructs). You may use the patterns defined by us or define your own patterns. User defined patterns may describe a code template to be matched, or an operation over the [Spoon](https://github.com/INRIA/spoon) AST.

Consult the [project's user manual](USAGE.md) for more information.

## Usage
```
java -jar pattern_matcher.jar <filename|foldername> [<userSettings.json>] [DEBUG]
```

* ```filename/foldername``` denotes de target java file or project folder;
* ```userSettings.json``` denotes the program settings (which patterns should be tested, which operations to report on, number of threads to use, _etc._) - provided optionally;
* ```DEBUG``` denotes the debug flag, meaning whether the run should report execution details - provided optionally.

## Overview

## Testsuite
We used the jUnit testing framework for quality assurance. We strove for 100% method coverage on all types of Workers (pattern definitions), as well as the pattern matching engine itself.

The testsuite can be run simply by setting up the project in IntelliJ, mark the src/test folder as containing test files, and clicking "Run all tests".

![Tests Coverage](https://i.imgur.com/gpauNM1.png)


## Completed tasks
See the [project's kanban board](https://github.com/msramalho/feup-comp/projects/1) for live updates on the project's completion.

 1. Configurations from JSON file (a [default](project/src/UserSettings.json) exists) uses [GSON](https://github.com/google/gson)
 1. Build a [Spoon](https://github.com/INRIA/spoon) model from a given source file or folder
 1. Implement concurrent architecture to trigger workers, with threadpool
 1. Discover all the classes fed into Spoon so as to trigger [ClassScanner](project/src/main/ClassScanner.java) instances
 1. Implement WorkerFactory and create one for each type of worker
 1. Implement Static and Dynamic Worker Factories
     * Static workers have a _trigger_ node and are more flexible as they allow the user to play with Spoon (uses reflection to find Workers defined in the configuration - if configuration is `countIfs`, worker should be `W_countIfs`)
     * Dynamic workers access a [Patterns](project/patterns/Patterns.java) file and try to match them
 1. Implement a CtElement iterator to go through the AST in DFS order. Accepted [PR](https://github.com/INRIA/spoon/pull/1980) in spoon. 
 1. Extend CtElement to be iterable in forLoops. Accepted in [PR](https://github.com/INRIA/spoon/pull/1986) in spoon.
 1. Recursive DFS collection of Worker Results
 1. Creation of a horizontal Report from the triggered workers
 1. Implementation of Streams to merge WorkerReports with custom functions, for instance: sum, count, avg, ... Using [guava](https://github.com/google/guava)
 1. Inclusion of unmerged Spoon [PR](https://github.com/INRIA/spoon/pull/1686) that implements template matching - public discussion on how to use it in this [issue](https://github.com/INRIA/spoon/issues/1989)
 1. Creation of simple Patterns that match variables only
 1. Implementation of many static Patterns, such as cyclomatic complexity, inner loop max depth, ...
 1. Creation of tests for static and dynamic patterns
 1. [WIP] trying to apply the "any" template matcher to our project


## PR Spin-Offs
We needed to iterate through the nodes in the AST in depth-first manner, and this functionality was not available in Spoon.
As such, we decided to implement it and submit a pull request, additionally closing the oldest open Spoon issue at the time.
* [Spoon#1980](https://github.com/INRIA/spoon/pull/1980): implementation of a CtIterator class, enabling depth-first-search from any node of the AST;
* [Spoon#1986](https://github.com/INRIA/spoon/pull/1986): interface enabling iteration over a CtElement's descendants, using range-based for loops.

## Additional Links
* [Spoon documentation](http://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/)
* [Project Specification](https://docs.google.com/document/d/1-DK3CyzAkquQKJf0ci8Heed-OskWz9QQfpMw78eJ6BI/edit?usp=sharing)
* [BitBucket Repo](https://bitbucket.org/FEUP_COMP1718/g41)
* [GitHub Repo](https://github.com/msramalho/feup-comp)
