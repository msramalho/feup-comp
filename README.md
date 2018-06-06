# Project Title: Multipurpose Pattern Matcher

## Group: G41

NAME1: André Miguel Ferreira da Cruz, NR1: 201503776, GRADE1: 19, CONTRIBUTION1: 33,(3)%

NAME2: Edgar Filipe Gomes Carneiro, NR2: 201503776, GRADE2: 19, CONTRIBUTION2: 33,(3)%

NAME3: Miguel Sozinho Ramalho, NR3: 201403027, GRADE3: 19, CONTRIBUTION3: 33,(3)%

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
Our tool receives a source code file/folder for analyzing, and, optionally, a _settings.json_ file indicating which patterns to run, which operations to perform on the results, input/output paths, and number of threads to use. After parsing command line arguments, setting up configurations, and creating _WorkerFactories_ for each type of _Worker_ (pattern analyzer) to be run, we execute a _ClassScanner_ for each class set to be analyzed, in parallel.

A _ClassScanner_ extends Spoon's _CtScanner_, and, thus, iteratively searches the class file's AST in a depth-first manner. For each node traversed, it triggers the appropriate _Worker_ instances (found by a request to the _FactoryManager_ class, and matched by a _TypeFilter_). Each worker is run in parallel, and _Futures_ of the task's execution are kept in order to later retrieve the execution report. By traversing the AST, we also construct an equally shaped tree of _Nodes_, to keep tabs on execution reports, and to be able to retrieve these reports from every part of the program (be it a single method, an inner class, or the global file).

Information regarding how to define patterns and how they work can be found in the [project's user manual](USAGE.md).


## Testsuite
We used the jUnit testing framework for quality assurance. We strove for 100% method coverage on all types of Workers (pattern definitions), as well as the pattern matching engine itself.

The testsuite can be run simply by setting up the project in IntelliJ, mark the src/test folder as containing test files, and clicking "Run all tests".

![Tests Coverage](https://i.imgur.com/gpauNM1.png)


## Task Distribution
In general, each group member participated in every aspect of the project's development.
The group member Edgar Carneiro worked more closely with the code pattern matching engine, integrating it on the existing project architecture, and providing several configuration options.
The group members André Cruz and Miguel Ramalho worked more closely with the _ClassScanner_-_WorkerFactory_-_Worker_ architecture, and the _Report_ collection innerworks.

## Pros
* Felxible architecture that can be extended, in any of the following ways:
    * Inclusion of more static Patterns
    * Use of more information from each Pattern Worker
    * Implementation of new operations on the results of a Pattern, i.e. quartiles, skewness, kurtosis, ...
* Off-the-shelf implementation of many patterns, that serve as examples for expansion to other patterns
* Pattern Report is fetcheable from any node in the AST, as it is done recursively
* Ability to use template matching patterns, that is generaly more accessible for users
* Ability yo use Spoon-based pattern matching functionality for users that want more flexibility
* Configurations file is both expandable and shareable, it can also be shared between users so as to guarantee the same execution environment


## Cons
* 


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
