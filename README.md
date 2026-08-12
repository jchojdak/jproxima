# JProxima

> [!WARNING]
> This library is compiled with Java 21. Running it on older JVMs is not supported.

```
 _______ ______                _
(_______|_____ \              (_)
     _   _____) )___ ___ _   _ _ ____  _____
 _  | | |  ____/ ___) _ ( \ / ) |    \(____ |
| |_| | | |   | |  | |_| ) X (| | | | / ___ |
 \___/  |_|   |_|   \___(_/ \_)_|_|_|_\_____|
```

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
- **File I/O**: Read and write CSV (.csv) and Excel (.xlsx) files
- **Fluent API**: Intuitive data manipulation
- **Type-safe**: Automatic type inference and strong type safety via DataType
- **DataFrame operations**: Join (INNER, LEFT, RIGHT, FULL), drop column
- **Column operations**: Statistics (min, max, avg, sum, count, nullCount)

## Installation

Include the JProxima library in your project using your preferred dependency management system (Maven/Gradle).

> Replace `0.4.0` with the latest version available on releases.

For example, using Maven:

```xml
<dependencies>
    <!-- use the latest version -->
    <dependency>
        <groupId>com.jchojdak</groupId>
        <artifactId>jproxima-api</artifactId>
        <version>0.4.0</version>
    </dependency>
    <dependency>
        <groupId>com.jchojdak</groupId>
        <artifactId>jproxima-impl</artifactId>
        <version>0.4.0</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

Using Gradle (Groovy DSL):

```groovy
dependencies {
    // use the latest version
    implementation "com.jchojdak:jproxima-api:0.4.0"
    runtimeOnly "com.jchojdak:jproxima-impl:0.4.0"
}
```

Using Gradle (Kotlin DSL):

```kotlin
dependencies {
    // use the latest version
    implementation("com.jchojdak:jproxima-api:0.4.0")
    runtimeOnly("com.jchojdak:jproxima-impl:0.4.0")
}
```

## Examples

### End-to-end example

```java
import com.jchojdak.jproxima.data.*;
import com.jchojdak.jproxima.io.DataFrameReader;

public class Example {
    public static void main(String[] args) {

        // 1. Read CSV files
        DataFrame customers = DataFrameReader.csv("customers.csv")
                .delimiter(',')
                .header(true)
                .build();

        DataFrame orders = DataFrameReader.excel("orders.xlsx")
                .sheet("orders1")
                .header(true)
                .build();

        // 2. Join DataFrames
        DataFrame joined = customers.join(
                orders,
                JoinType.INNER,
                "customer_id",
                "customer_id",
                "_x",
                "_y"
        );

        // 3. Select and manipulate data
        DataFrame selected = joined.getColumns("customer_id", "name", "amount");
        DataFrame withoutId = selected.dropColumn("customer_id");

        // 4. Preview data
        System.out.println(selected.head(5)); // first 5 rows
        System.out.println(selected.tail(5)); // last 5 rows

        // 5. Column statistics
        var stats = joined.getColumn("amount").stats();

        System.out.printf("""
                        Min: %s
                        Max: %s
                        Avg: %s
                        Sum: %s
                        Count: %d
                        Nulls: %d
                        """,
                stats.min(),
                stats.max(),
                stats.avg(),
                stats.sum(),
                stats.count(),
                stats.nullCount()
        );

        // 6. Access values
        String firstName = joined.getColumn("name").get(0).toString();
        int size = joined.getColumn("name").size();

        // 7. Print values
        System.out.println("First name: " + firstName);
        System.out.println("Column size: " + size);

        // 8. Save results
        selected.toCsv("output.csv");
        selected.toExcel("output.xlsx");
    }
}
```