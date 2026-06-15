package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.*;
import com.jchojdak.jproxima.data.JoinType;
import com.jchojdak.jproxima.impl.data.join.DataFrameJoiner;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class DefaultDataFrameTest {

    @Test
    void shouldReturnZeroColumnCountForEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create().build();

        assertEquals(0, df.columnCount());
    }

    @Test
    void shouldReturnCorrectColumnCount() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        assertEquals(2, df.columnCount());
    }

    @Test
    void shouldGetColumnByName() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .build();

        Column result = df.getColumn("col1");

        assertEquals("col1", result.getName());
        assertEquals(3, result.size());
    }

    @Test
    void shouldGetMultipleColumnsByNames() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .addColumn("col3", new Object[]{true, false})
                .build();

        DataFrame result = df.getColumns("col1", "col3");

        assertEquals(2, result.columnCount());
        assertNotNull(result.getColumn("col1"));
        assertNotNull(result.getColumn("col3"));
        assertNull(result.getColumn("col2"));
    }

    @Test
    void shouldReturnColumnNamesInOrder() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        List<String> names = df.getColumnNames();

        assertAll(
                () -> assertEquals(2, names.size()),
                () -> assertEquals(List.of("col1", "col2"), names)
        );
    }

    @Test
    void shouldAddNewColumnWithoutName() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        Column newColumn = StringColumnBuilder.init()
                .name("col2")
                .add(new String[]{"a", "b"})
                .build();

        DataFrame result = df.addColumn(newColumn);

        assertEquals(2, result.columnCount());
        assertEquals(newColumn, result.getColumn("col2"));
    }

    @Test
    void shouldAddNewColumnWithName() {
        String name = "col2";

        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        Column newColumn = StringColumnBuilder.init()
                .name(name)
                .add(new String[]{"a", "b"})
                .build();

        DataFrame result = df.addColumn(name, newColumn);

        assertEquals(2, result.columnCount());
        assertEquals(newColumn, result.getColumn(name));
    }

    @Test
    void shouldDropColumn() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        DataFrame result = df.dropColumn("col1");

        assertEquals(1, result.columnCount());
        assertNull(result.getColumn("col1"));
        assertNotNull(result.getColumn("col2"));
    }

    @Test
    void shouldReturnEmptyDataFrameWhenHeadCalledOnEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create().build();

        DataFrame result = df.head(5);

        assertEquals(0, result.rowCount());
        assertEquals(0, result.columnCount());
    }

    @Test
    void shouldReturnFirstNRowsWhenHeadCalled() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3, 4, 5})
                .build();

        DataFrame result = df.head(3);

        assertEquals(3, result.rowCount());
        assertEquals(1, result.getColumn("col1").get(0));
        assertEquals(2, result.getColumn("col1").get(1));
        assertEquals(3, result.getColumn("col1").get(2));
    }

    @Test
    void shouldReturnEmptyDataFrameWhenTailCalledOnEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create().build();

        DataFrame result = df.tail(5);

        assertEquals(0, result.rowCount());
        assertEquals(0, result.columnCount());
    }

    @Test
    void shouldReturnLastNRowsWhenTailCalled() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3, 4, 5})
                .build();

        DataFrame result = df.tail(3);

        assertEquals(3, result.rowCount());
        assertEquals(3, result.getColumn("col1").get(0));
        assertEquals(4, result.getColumn("col1").get(1));
        assertEquals(5, result.getColumn("col1").get(2));
    }

    @Test
    void shouldReturnAllRowsWhenTailRequestsMoreThanAvailable() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        DataFrame result = df.tail(10);

        assertEquals(2, result.rowCount());
    }

    @Test
    void shouldConvertToArrayCorrectly() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        Object[][] result = df.toArray();

        assertEquals(2, result.length);
        assertEquals(2, result[0].length);
        assertEquals(1, result[0][0]);
        assertEquals("a", result[0][1]);
        assertEquals(2, result[1][0]);
        assertEquals("b", result[1][1]);
    }

    @Test
    void shouldReturnEmptyArrayForEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create().build();

        Object[][] result = df.toArray();

        assertEquals(0, result.length);
    }

    @Test
    void shouldReturnStringForNonEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .addColumn("col2", new Object[]{"a", "b", "c"})
                .build();

        String result = df.toString();

        assertTrue(result.contains("col1"));
        assertTrue(result.contains("col2"));

        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
    }

    @Test
    void shouldRespectDisplayLimitInToString() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3, 4, 5})
                .build();

        String result = df.toString(3);

        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));

        assertFalse(result.contains("4"));
        assertFalse(result.contains("5"));

        assertTrue(result.contains("... (2 more rows)"));
    }

    @Test
    void shouldReturnEmptyDataFrameStringForEmptyDataFrame() {
        DataFrame df = DataFrameBuilder.create().build();

        String result = df.toString();

        assertEquals("<Empty DataFrame>", result);
    }

    @Test
    void shouldReturnCorrectDataFrameEquals() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .addColumn("col2", new Object[]{"a", "b", "c"})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .addColumn("col2", new Object[]{"a", "b", "c"})
                .build();

        assertEquals(df1, df2);
    }

    @Test
    void shouldReturnFalseWhenComparedToNull() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .build();

        assertNotEquals(null, df);
    }

    @Test
    void shouldReturnFalseWhenRowCountsDiffer() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        assertNotEquals(df1, df2);
    }

    @Test
    void shouldReturnFalseWhenColumnCountsDiffer() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        assertNotEquals(df1, df2);
    }

    @Test
    void shouldReturnFalseWhenColumnNamesAreDifferent() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col2", new Object[]{1, 2, 3})
                .build();

        assertNotEquals(df1, df2);
    }

    @Test
    void shouldReturnFalseWhenColumnOrderIsDifferent() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col2", new Object[]{"a", "b"})
                .addColumn("col1", new Object[]{1, 2})
                .build();

        assertNotEquals(df1, df2);
    }

    @Test
    void shouldReturnFalseWhenColumnValuesAreDifferent() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 99})
                .build();

        assertNotEquals(df1, df2);
    }

    @Test
    void shouldReturnTrueForTwoEmptyDataFrames() {
        DataFrame df1 = DataFrameBuilder.create().build();
        DataFrame df2 = DataFrameBuilder.create().build();

        assertEquals(df1, df2);
    }

    @Test
    void shouldReturnCorrectDataFrameHashCode() {
        DataFrame df1 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .addColumn("col2", new Object[]{"a", "b", "c"})
                .build();

        DataFrame df2 = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2, 3})
                .addColumn("col2", new Object[]{"a", "b", "c"})
                .build();

        assertEquals(df1.hashCode(), df2.hashCode());
    }

    @Test
    void shouldDelegateJoinToDataFrameJoiner() {
        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2, 3})
                .addColumn("name", new Object[]{"Alice", "Bob", "Charlie"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{2, 3, 4})
                .addColumn("city", new Object[]{"Warsaw", "Krakow", "Pulawy"})
                .build();

        DataFrame mockResult = DataFrameBuilder.create()
                .addColumn("id", new Object[]{2, 3})
                .build();

        List<String> leftKeys = List.of("id");
        List<String> rightKeys = List.of("id");
        String leftSuffix = "_left";
        String rightSuffix = "_right";

        try (MockedStatic<DataFrameJoiner> mocked = mockStatic(DataFrameJoiner.class)) {
            mocked.when(() -> DataFrameJoiner.join(
                            left,
                            right,
                            JoinType.INNER,
                            leftKeys,
                            rightKeys,
                            leftSuffix,
                            rightSuffix
                    ))
                    .thenReturn(mockResult);

            DataFrame result = left.join(
                    right,
                    JoinType.INNER,
                    leftKeys,
                    rightKeys,
                    leftSuffix,
                    rightSuffix
            );

            assertEquals(mockResult, result);

            mocked.verify(() -> DataFrameJoiner.join(
                    left,
                    right,
                    JoinType.INNER,
                    leftKeys,
                    rightKeys,
                    leftSuffix,
                    rightSuffix
            ), times(1));
        }
    }
}