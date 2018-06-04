# Multipurpose Pattern Matcher

Our java project is a flexible pattern matching tool for dynamic (compiled at runtime) and static (more freedom, but require coding) pattern definitions.

This project uses the open-source [Spoon](https://github.com/INRIA/spoon) library and its AST for pattern matching.

## Usage

### On the Command Line
```
java main.Main <filename|foldername> [<userSettings.json>]
```

* ```filename/foldername``` denotes de target file/folder;
* ```userSettings.json``` denotes the pattern settings (which ones should execute, which operations to report on, _etc._);


### Configurations: UserSettings.json
This configurations file contains a set of optionable configurations:
* report output path;
* dynamic patterns file path;
* operations to perform;
* number of threads to use in execution;
* which static workers to run (a boolean for each, false by default);

### Dynamic Pattern Definitions

Dynamic pattern definitions are provided in a ```Patterns``` class, written by you, in order to define templates for patterns to be matched.

```java
public class Patterns {
    Integer _var_y_;

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 500;
        } else {
            _var_y_ = 1000;
        }
    }
}

```


### Static Pattern Definitions

Static pattern definitions give you complete freedom over the type of patterns you want to build, as long as they operate on the Spoon AST.

This type of pattern definitions can be incorporated by creating a new class with name ```W_<pattern_name>```, in which <pattern_name> denotes the pattern's unique name.
This class must be placed on the worker.workers package, and must properly extend the ```Worker``` class. The rest of the linking will be taken care of by runtime reflection.

Besides the ```call()``` method, this ```W_<pattern_name>``` class can define what operations you would like to have on the pattern's report by overriding the ```getOperations()``` method. This method must return a mapping from the operation's name to the operation's function, ```Map<String, Function<Stream<WorkerReport>, Number>>```.
Some common operations have already been implemented (sum, avg, min, max, standard deviation, median), but you can define your own as long as they follow the provided interface.


### Notes:
If used from the java sources, a report can be obtained on any node of the code's AST. That is, you can obtain the global report by calling the ```getReport()``` method on the root node, as well as any intermediary reports on any os its descendant nodes.
(Each node's report includes its descendants' reports)
