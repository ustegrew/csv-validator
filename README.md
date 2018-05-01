# CSV validator

Does what it says on the label: Validates a given CSV file against a set of policies.

* Validation scans data and streams the result to a client provided receptor. This makes it possible to scan very large amounts of data (e.g. 500 MB files) without hitting the memory limit. 
* The program supports validation of files and Strings. 
* This software is still in early alpha stage; it doesn't even fully compile yet as some of the classes aren't finished. 
* Development won't implement many features, but concentrate on delivering a program that 'just works' and interoperates easily with the client (= works well with other software).
* Program is designed for ease of use, i.e. get validation with minimum effort.

## Use

### Design your policy

The validator is driven by a policy which checks the given data according to a set of rules. To build a policy you need to create an object of type `org.ph394b8fe.validator.policy.TPolicy` and augment it with the necessary rules. All rule parameters are passed as strings. This makes it easy to retrieve those parameters from elsewhere, e.g. a database, configuration files, ...

```java
TPolicy policy = new TPolicy ()
    .withEncoding       ("UTF-8")
    .withDelimiter      (",")
    .withEmptyLines     ("true")
    .withHeaderAtLine   ("1")
    .withLayout 
    (
        new TRuleRecordLayout ()
            .withField      
            (
                "ID",
                VType.createInstance ("int").withOption ("min", "1"),
                "ID must be an integer >= 1"
            )
            .withField
            (
                "",
                VType.createInstance ("void"),
                "Second field must be empty"
            )
            .withField
            (
                "FIRST_NAME",
                VType.createInstance ("rx").withOption ("canBeEmpty", "true").withOption ("regx", "[a-zA-Z]+"),
                "FIRST_NAME: Only lower and upper case characters, can be empty"
            )
            .withField
            (
                "SECOND_NAME",
                VType.createInstance ("rx").withOption ("regx", "[a-zA-Z]+").withOption ("canBeEmpty", "true"),
                "SECOND_NAME: Only lower and upper case characters, can be empty"
            )
            .withField
            (
                "AGE",
                VType.createInstance ("int").withOption ("min", "1").withOption ("canBeEmpty", "true"),
                "AGE: Must be an integer >= 1, can be empty"
            )
    ).done ();
```

The closing call to `done()` is mandatory, it checks the entire configuration for sanity and throws the necessary exceptions when the configuration is faulty. All exceptions are `RuntimeExceptions`, i.e. not checked. If the policy's configuration is wonky we consider it a bug - the program simply fails, and you need to fix the policy setup first.


### Instantiate validator

Once the policy stands you need to instantiate the validator and pass the policy to it:

```java
TCSVValidator validator = new TCSVValidator (policy);
```

### Receiving feedback

Whilst the validator is working it streams its findings to a receptacle. That is a class which implements `org.ph394b8fe.validator.IReceptacle` . You need to write that class yourself. It has one single method, `push` where the validator will push it's findings. Each finding is a object of type `org.ph394b8fe.validator.result.TResult`; you can use it to extract the findings and use them as you need.

```java
import org.ph394b8fe.validator.TCSVValidator.IReceptacle;
import org.ph394b8fe.validator.result.TResult;

public class TMyReceptacle implements IReceptacle
{
    @Override
    public void push (TResult result)
    {
        /* Your handling code here */
    }
}
```

### Running the validation

Once your policy is set up and your receptacle available you call the method `process` and pass a `TReceptacle` instance to it.

```java
receptor = new TMyReceptacle ();
validator.process (csvData, receptor);
```

The parameter `csvData` can be a `java.io.File` pointing to your csv file or a String carrying csv data.

When the method executes the validator will read the file (data) and stream `TReport` objects to your receptacle via its `push`method.


### Full example

See Class `org.ph394b8fe.TMain`


##

Roadmap:

* Make it work, i.e. finish incomplete classes.
* Write documentation
* Test it with various CSV corpuses in file and string form.
 
### org.ph394b8fe - weird name...

Yes, it's weird - the ph394b8fe is just ph and some random characters thrown together. I needed some name for the packages.
