# Multipurpose Pattern Matcher

Our java project is a flexible pattern matching tool for dynamic (compiled at runtime) and static (more freedom, but require coding) pattern definitions.

This project uses the open-source [Spoon](https://github.com/INRIA/spoon) library and its AST for pattern matching.

## Usage

### On the Command Line
```
java main.Main <filename|foldername> [<userSettings.json>] [DEBUG]
```

* ```filename/foldername``` denotes de target java file or project folder;
* ```userSettings.json``` denotes the program settings (which patterns should be tested, which operations to report on, number of threads to use, _etc._) - provided optionally;
* ```DEBUG``` denotes the debug flag, meaning whether the run should report in execution details - provided optionally.


### Configurations: UserSettings.json
This configurations file contains a set of optional configurations:
* report output path and format;
* dynamic patterns file path;
* operations to perform;
* number of threads to use in execution;
* which static workers to run (a boolean for each, false by default);

Here is a sample configurations file:
```json
{
	"global": {
		"numberOfThreads": 32,
		"parseComments": false,
		"outputPath": "out",
		"operations": ["sum", "min", "max", "std", "avg"]
	},
	"dynamic": {
		"patternsFile": "../project/patterns/Patterns.java"
	},
	"static": {
		"innerLoops": true,
		"possibleTernary": true
	}
}
```

A configuration file that activates all configurations can be found [here](https://github.com/msramalho/feup-comp/blob/master/project/UserSettings.json)

### Dynamic Pattern Definitions

Dynamic pattern definitions are provided in a ```Patterns``` class, written by you, in order to define templates for patterns to be matched.

Here is an example of a template for identifying possible uses of a ternary operator:
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
This class must be placed on the worker.workers package, and must properly extend the ```Worker``` class. The rest of the linking will be taken care of by our project's code.

#### Defining a Pattern by use of the Spoon AST
In order to define a pattern, your worker class (which must comply with the previously stated rules) should override the ```call()``` and ```setFilter()``` methods. The worker's _call_ method will be called on every node in the AST that matches the worker's _TypeFilter_ (set on the setFilter method).

Moreover, the _call_ method must return a _WorkerReport_ instance, reporting on the worker's execution as you see fit. The _WorkerReport_ constructor receives a single integer value, possibly denoting the number of matches the worker found (but you can use it as you see fit). You can also extend the _WorkerReport_ class to include more detailed reports of the worker's execution, and then create your own _Operations_ to aggregate this reports.

An example of an overridden _setFilter_ method that matches with For-loop nodes:
```java
@Override
protected AbstractFilter setFilter() {
	return new TypeFilter<>(CtFor.class);
}
```

And the associated _call_ method for counting the number of For-loops:
```java
@Override
public WorkerReport call() throws Exception {
	return new WorkerReport(1);
}
```

#### Defining Operations
Additionally, you worker class can define what operations you would like to feature on the pattern's report. This operations will be used to aggregate the worker's _WorkerReport_, which you also have full control over.

You define operations by overriding the ```getOperations()``` method. This method must return a mapping from the operation's name to the operation's function, ```Map<String, Function<Stream<WorkerReport>, Number>>```.
Some common operations have already been implemented (sum, avg, min, max, standard deviation, median), but you can define your own as long as they follow the provided interface.

For example, here is the overridden _getOperations_ method supplying the average operation:
```java
@Override
public Map<String, Function<Stream<WorkerReport>, Number>> getOperations() {
	Map<String, Function<Stream<WorkerReport>, Number>> operations = new HashMap<>();

	operations.put("average", (Stream<WorkerReport> s) -> 
		s.mapToInt(WorkerReport::getValue).average().orElse(0)
	);

	return operations;
}
```

## Off-the-shelf Patterns
In order to demonstrate the usage and make this project useful as-is, we have implemented a number of patterns both using static and dynamic matching. 

### Static Patterns
The most straightforward patterns include:
 * Comments identification (Any, Inline, Block and Javadoc) [these patterns require the configuration `global.parseComments` to be `true`]: `classComments`,`classCommentsBlock`,`classCommentsInline`,`classCommentsJavadoc`,`classComments`
 * Class Fields identification (Any, public, protected, private, static): `classFields`, `classFieldsPublic`, `classFieldsProtected`, `classFieldsPrivate`, `classFieldsStatic`
 * Class Methods identification (Any, abstract, public, protected, private, static): `classMethods`, `classMethodsAbstract`, `classMethodsPublic`, `classMethodsProtected`, `classMethodsPrivate`, `classMethodsStatic` 
 * Lines of Code (Class, Method) [these patterns are done on a standardize format of the code, also the results will vary with the configuration `global.parseComments`]: `linesOfCodeClass`, `linesOfCodeMethod`
 * Java Statements identification (For loop, For-each loop, While loop, Do-while loop, If statement, Switch statement, Ternary operator): `loopsFor`, `loopsForeach`, `loopsWhile`, `loopsDoWhile`, `conditionalId`, `conditionalSwitch`, `ternary`
 
Some more complex pattern were also implemented in this manner, namely:
 * Cyclomatic Complexity of a method: `cyclomaticComplexity`
 * Maximum inner loop depth of a method: `innerLoops`
 * Super Class count (excluding Classes from `java.*` packages): `superClass`
 * Super Class count (including Classes from `java.*` packages): `superClassJava`
 * Weighted Method Count (WMC) based on Cyclomatic complexity: `weightedMethodCountCC`
 * Weighted Method Count (WMC) based on Lines of Code per Method: `weightedMethodCountLoC`
 * Weighted Method Count (WMC) based on Number of Methods: `weightedMethodCountNoM`


### Dynamic Patterns


### Additional Notes:
If used from the java sources, a report can be obtained on any node of the code's AST. That is, you can obtain the global report by calling the ```getReport()``` method on the root node, as well as any intermediary reports on any os its descendant nodes.
(Each node's report includes its descendants' reports)
