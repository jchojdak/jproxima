# JProxima
JProxima is a Java library for working with data and machine learning.

## Examples

### Creating Column
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
Creates a column named "testColumn" containing string values.

### Creating DataFrame
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
Creates a DataFrame with three columns of different types.

### Selecting Columns
```java
DataFrame selected = df.getColumns("testColumn1", "testColumn3");
```
Returns a new DataFrame containing only the specified columns.

### Dropping Columns
```java
DataFrame updated = df.dropColumn("testColumn3");
```

### First or last n rows
```java

DataFrame firstTwo = df.head(2); // first 2 rows
DataFrame lastTwo = df.tail(2);  // last 2 rows
```
Easily retrieve the first or last n rows as a new DataFrame.

### Converting to array
```java
Object[][] dataArray = df.toArray();
```
Converts the entire DataFrame into a 2D Object

### Accessing Column values
```java
Column testColumn1 = df.getColumn("testColumn1");
Object value = testColumn1.get(0); // get first value
int size = testColumn1.size(); // number of rows
```
Access individual values or the total size of a column.