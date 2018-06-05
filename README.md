# Compilers Project
This Project is a multiporpose pattern matching tool, enabling you to analyse whole projects for specific code patterns (_e.g._ use of ternary operators instead of if-else constructs). You may use the patterns defined by us or define your own patterns.

Consult the [project's user manual](USAGE.md) for more information.

Also...
* [Spoon documentation](http://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/)
* [Project Specification](https://docs.google.com/document/d/1-DK3CyzAkquQKJf0ci8Heed-OskWz9QQfpMw78eJ6BI/edit?usp=sharing)
* [BitBucket Repo](https://bitbucket.org/FEUP_COMP1718/g41)
* [GitHub Repo](https://github.com/msramalho/feup-comp)

# Completed tasks
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
 1. [WIP] trying to apply the "any" template matcher to our project
