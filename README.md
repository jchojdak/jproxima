# JProxima

> [!WARNING]
> This library is compiled with Java 21. Running it on older JVMs is not supported.

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-light.svg)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=bugs)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jchojdak_jproxima&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jchojdak_jproxima)

## About

JProxima is a JVM library (Java/Kotlin/Scala and more) that provides tools for data manipulation and machine learning
model building.
The library is structured into two modules: API and implementation (impl). Developers interact only with stable API,
while the impl module is decoupled and can be easily swapped or extended, allowing flexibility and maintainability
without changing end-user code.

## Features

- **Immutable data structures**: Thread-safe DataFrame and Column data structures
- **File I/O**: Read/write CSV and Excel (.xlsx) files
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
        <version>0.3.0</version><!-- use the latest version -->
    </dependency>
    <dependency>
        <groupId>com.jchojdak</groupId>
        <artifactId>jproxima-impl</artifactId>
        <version>0.3.0</version><!-- use the latest version -->
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Examples

### End-to-end example

```java
import com.jproxima.api.dataframe.*;
import com.jproxima.api.column.*;

public class Example {
    public static void main(String[] args) {

        // 1. Read CSV file
        DataFrame df = DataFrameReader.csv("data.csv")
                .delimiter(',')
                .header(true)
                .build();

        // 2. Create additional columns
        StringColumn names = StringColumnBuilder.init()
                .name("name")
                .add("Alice", "Bob", "Charlie")
                .build();

        IntColumn ages = IntColumnBuilder.init()
                .name("age")
                .add(25, 30, 35)
                .build();

        BooleanColumn active = BooleanColumnBuilder.init()
                .name("active")
                .add(true, false, true)
                .build();

        // 3. Build new DataFrame
        DataFrame newDf = DataFrameBuilder.create()
                .addColumn(names)
                .addColumn(ages)
                .addColumn(active)
                .build();

        // 4. Select and manipulate data
        DataFrame selected = newDf.getColumns("name", "age");
        DataFrame withoutAge = newDf.dropColumn("age");

        // 5. Preview data
        System.out.println(selected.head(2)); // first 2 rows
        System.out.println(selected.tail(2)); // last 2 rows

        // 6. Access values
        Column nameColumn = newDf.getColumn("name");
        Object firstName = nameColumn.get(0);
        int size = nameColumn.size();

        System.out.println("First name: " + firstName);
        System.out.println("Column size: " + size);

        // 7. Convert to array
        Object[][] array = newDf.toArray();

        // 8. Save results
        selected.toCsv("output.csv");
        selected.toXlsx("output.xlsx");
    }
}