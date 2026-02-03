# JProxima

> [!WARNING]
> This library is compiled with Java 21. Running it on older JVMs is not supported.

## About
JProxima is a JVM library (Java/Kotlin/Scala and more) that provides tools for data manipulation and machine learning model building.
The library is structured into two modules: API and implementation (impl). Developers interact only with stable API,
while the impl module is decoupled and can be easily swapped or extended, allowing flexibility and maintainability without changing end-user code.

## Features
 - **Immutable data structures**: Thread-safe DataFrame and Column data structures
 - **File I/O**: Read CSV and Excel files into DataFrame objects
 - **Fluent API**: Builder pattern for intuitive data manipulation
 - **Type-safe**: Automatic type inference and strong type safety via DataType

## Installation
Include the JProxima library in your project using your preferred dependency management system (Maven/Gradle).

For example, using Maven:
```xml
<dependencies>
    <dependency>
        <groupId>com.jchojdak</groupId>
        <artifactId>jproxima-api</artifactId>
        <version>0.1.2</version><!-- use the latest version -->
    </dependency>
    <dependency>
        <groupId>com.jchojdak</groupId>
        <artifactId>jproxima-impl</artifactId>
        <version>0.1.2</version><!-- use the latest version -->
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Examples

### Reading files
**CSV**
```java
DataFrame df = DataFrameReader.csv("data.csv")
        .delimiter(',') // optional: default is ,
        .header(true) // optional: default is true
        .quote('"') // optional: default is "
        .escape('\\') // optional: default is '\\'
        .encoding("UTF-8") // optional: default is UTF-8
        .nullValue("NA") // optional: default is ""
        .skipRows(1) // optional: default is 0
        .build();
```

**Excel**

*TODO: Not implemented yet.*
```java
DataFrame df = DataFrameReader.excel("data.xlsx")
        .sheet("Sheet1") // required
        .header(true) // optional: default is true
        .build();
```

**Auto-detect file type**
```java
DataFrame dfCsv = DataFrameReader.read("data.csv");
DataFrame dfExcel = DataFrameReader.read("data.xlsx");
```

### Creating column
Creates a column named "testColumn" containing string values.
```java
String[] data = {"value1", "value2", "value3"};

Column column = ColumnBuilder.create()
        .name("testColumn")
        .type(DataType.STRING)
        .addAll(data)
        .add("value4")
        .add("value5")
        .build();
```

### Creating DataFrame
Creates a DataFrame with three columns of different types.
```java
String[] data = {"value1", "value2", "value3"};

Column column = ColumnBuilder.create()
        .name("testColumn1")
        .type(DataType.STRING)
        .addAll(data)
        .build();

DataFrame df = DataFrameBuilder.create()
        .addColumn(column)
        .addColumn("testColumn2", new String[]{"a", "b", "c"})
        .addColumn("testColumn3", new Integer[]{1, 2, 3})
        .build();
```

### Selecting columns
Returns a new DataFrame containing only the specified columns.
```java
DataFrame selected = df.getColumns("testColumn1", "testColumn3");
```

### Dropping columns
```java
DataFrame updated = df.dropColumn("testColumn3");
```

### First or last n rows
Easily retrieve the first or last n rows as a new DataFrame.
```java
DataFrame firstTwo = df.head(2); // first 2 rows
DataFrame lastTwo = df.tail(2);  // last 2 rows
```

### Printing data
Prints the DataFrame in a tabular format.
```java
var df = DataFrameReader.read("data.csv");
System.out.println(df); // prints the DataFrame (default max 10 rows)
System.out.println(df.toString()); // prints the DataFrame (default max 10 rows)
System.out.println(df.toString(40)); // prints first 40 rows
```

### Converting to array
Converts the entire DataFrame into a 2D Object.
```java
Object[][] dataArray = df.toArray();
```

### Accessing column values
Access individual values or the total size of a column.
```java
Column testColumn1 = df.getColumn("testColumn1");
Object value = testColumn1.get(0); // get first value
int size = testColumn1.size(); // number of rows
```