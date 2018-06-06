# Multipurpose Pattern Matcher

Our java project is a flexible pattern matching tool for dynamic (compiled at runtime) and static (more freedom, but require coding) pattern definitions.

This project uses the open-source [Spoon](https://github.com/INRIA/spoon) library and its AST for pattern matching.

## Usage

### On the Command Line
```
java -jar pattern_matcher.jar <filename|foldername> [<userSettings.json>] [DEBUG]
```

* ```filename/foldername``` denotes de target java file or project folder;
* ```userSettings.json``` denotes the program settings (which patterns should be tested, which operations to report on, number of threads to use, _etc._) - provided optionally;
* ```DEBUG``` denotes the debug flag, meaning whether the run should report execution details - provided optionally.


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
            _var_y_ = 1;
        } else {
            _var_y_ = 2;
        }
    }
}

```

#### How to define a new Pattern

To define a new pattern you need to start by declaring a new method in your ```Patterns``` class. The name you give to the method is the name of the Pattern. This new method should not take any arguments. 

Exemplifying by creating a new pattern named "simpleDeclaration":
```java
public void simpleDeclaration() {}
```

Usually, when writing a Pattern you do not know what the name of the variables used in the code nor their content. If you do, and want to write a pattern only for those variables you can write something as:
```java
int i = 3;
```
In this example, only fragments of code that declare a variable named "i" and assign it the value "3" get matched with the pattern. However, this is rarely what you want and, therefore, we introduce you to the concept of __pattern variables__.

##### Pattern Variables

Pattern variables are variables that can take any value that satisfies its declaration. You can use them to represent any declaration, read, write or usage of a generic variable. To use a pattern variable you must first declare it outside of your methods, as an attribute of the Patterns class, such as:
```java
public class Patterns {
    int _var_x_;
    ...
 ```
In the example above, we are declaring a new pattern variable with the type ```int```. Notice how the variable is defined: for a variable to be interpreted as a pattern variable it needs to be written in the format: ```_var_SOMETHING_```. 
 
We can then use the pattern variable inside our pattern by referencing it as a normal variable
```java
int _var_x_ = 3;
```
When trying to match patterns the matcher will try to associate a real value to the pattern variable. Using the previous example ```_var_x_``` would be associated to ```i```. Naturally, fragments of code such as:
```java
Integer j = 3;
```
or
```java
int k;
k = 3;
```
would not be matched since they: in the first case, do not refer the same type; in the second case, represent different implementations, as a declaration (```int _var_x_ = ...```) is different from a write (```k = ...```) .


##### Pattern Methods

As a User, you may also want to reference generic methods in your patterns definitions. For that, you use __pattern methods__.

To use a patterns method you will have to declare a new abstract method with the name following the format ```_method_SOMETHING_```. Do not forget to make the Patterns class abstract too. For example, if you wanted a Pattern that would match all the assignments of the content of a function returning a _String_ to variables, you would have to write a Pattern similar to:
```java
public abstract class Patterns {
    String _var_x_;
    int _var_y_;
    abstract String _method_example_(int exampleArgument);
    
    public void example() {
        _var_x_ = _method_example_(_var_y_);
    }
}

```

##### Pattern Consumers

In addition to pattern methods and pattern variables, there are also __pattern consumers__. Pattern consumers are used to consume statements until they find the statement they are trying to match next. They are useful when there are statements with no value for the matching in between two valuable statements. For example, for when there are calls to methods between a variable declaration and its usage. If consumers did not exist, the User would not be able to match the variable declaration and its usage unless he knew exactly what was in between and defined it in the pattern.

There are two types of consumer that defer in consuming strategy: the lazy one and the greedy one. You can choose which to use by the way you declare the consumer. To use a consumer you must first declare an attribute `TemplateParameter<Void>` named either _lazy_any_ or _greedy_any_, such as:
```java
public class Patterns {
    TemplateParameter<Void> _lazy_any_;
    TemplateParameter<Void> _greedy_any_;
    ...
```
To then use it, inside the pattern u must call the attribute defined followed by the suffix `.S()`. Examplifying:
```java
public void example() {
if (true) {
    _lazy_any_.S();
    _var_x_ = 0;
    _greedy_any_.S();
}
```
In the example above, we defined a pattern named "example" that would match any `if` conditional statement with the condition being `true` and the body of the `Then Condition` containing a variable assignment of the value 0. Since we first use a lazy consumer, the first variable being assigned the value 0 will be associated with `_var_x_`. If we used a greedy consumer, the last variable being assigned the value 0 would be associated with `_var_x_`.

You can also configure your consumers by declaring the minimum and/or the maximum of statements it can consume. In order for you to do that, you must strictly follow the format below, where V and T represent positive integers, when declaring a consumer:
```java
_lazy_any_minV_;
_lazy_any_maxV_;
_lazy_any_minV_maxT_;
```
In this example we are always using a lazy consumer but, naturally, you can also use a greedy one. In the first sentence, we are defining a consumer that must consume a minimum of V statements. In the second statement, a consumer that cannot consume more than V statements. In the third one, a consumer that must consume at least V statements and no more than T statements.

##### Limitation
Dynamic Pattern matching has the limitation of only evaluating the first element on the root of each Pattern.
Therefore, Patterns with more than one element on their root will not be correctly matched.
To better understand this limitation, let us use an example:
```java
    public void example1() {
        while(true) {
	     System.out.println("example");
	     System.out.println("yey");
	}
	
        if (true) {_var_x_ = 2;} 
    }
    
    public void example2() {
        while(true) {
	     System.out.println("example");
	     System.out.println("yey");
	}
    }
```
In the example above, the matches of both patterns will be equal. They will both only match declarations of `while` cycles with the condition being `true` and the body equal to `System.out.println("example"); System.out.println("yey");`.

To overcome this limitation, you can define __Static Patterns__.


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

The default content of the default Patterns file declared in the [_UserSettings.json_ file](https://github.com/msramalho/feup-comp/blob/master/project/UserSettings.json) contains some example patterns. Its content is:
```java
import java.util.Collection;
import spoon.template.TemplateParameter;

public abstract class Patterns {

    Object _var_x_;
    Integer _var_y_;
    Boolean _var_bool_;
    Collection<Object> _var_z_;
    TemplateParameter<Void> _lazy_any_;
    TemplateParameter<Void> _greedy_any_;
    TemplateParameter<Void> _greedy_any_min1_;
    abstract Object _method_assign_();

    public void methodAssignment() {
        _var_x_ = _method_assign_();
    }

    public void possibleTernaryOperator() {
        if (true) {
            _var_y_ = 1;
        } else {
            _var_y_ = 2;
        }
    }

    public void nestedIfs() {
        if (_var_bool_) {
            _lazy_any_.S();
            if (_var_bool_) {
                _greedy_any_.S();
            }
            _greedy_any_.S();
        }
    }

    public void rangeBasedFor() {
        for (int i = 0; i < _var_z_.size(); i++) {
            _lazy_any_.S();
            _var_y_ = 0;
            _greedy_any_min1_.S();
        }
    }

}
```

There are also more examples of patterns in the test section. To visualize those patterns, visit the files present [here](https://github.com/msramalho/feup-comp/blob/master/project/src/test/testclasses/patterns/) that terminate in _Patterns.java_ or _PatternsX.java_, with X being a number.

### Additional Notes:
If used from the java sources, a report can be obtained on any node of the code's AST. That is, you can obtain the global report by calling the ```getReport()``` method on the root node, as well as any intermediary reports on any of its descendant nodes.
(Each node's report includes its descendants' reports)
