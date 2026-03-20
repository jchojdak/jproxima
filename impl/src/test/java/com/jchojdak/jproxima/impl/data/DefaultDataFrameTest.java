package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void shouldAddNewColumnWithoutName() {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .build();

        Column newColumn = ColumnBuilder.create()
                .name("col2")
                .type(DataType.STRING)
                .addAll(new Object[]{"a", "b"})
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

        Column newColumn = ColumnBuilder.create()
                .name(name)
                .type(DataType.STRING)
                .addAll(new Object[]{"a", "b"})
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
    void shouldWriteToCsv() throws IOException {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{1, 2})
                .addColumn("col2", new Object[]{"a", "b"})
                .build();

        Path tempFile = Files.createTempFile("test_df", ".csv");
        try {
            df.toCsv(tempFile.toString());

            List<String> lines = Files.readAllLines(tempFile);
            assertEquals(3, lines.size());
            assertEquals("col1,col2", lines.get(0));
            assertEquals("1,a", lines.get(1));
            assertEquals("2,b", lines.get(2));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void shouldEscapeSpecialCharactersInCsv() throws IOException {
        DataFrame df = DataFrameBuilder.create()
                .addColumn("col1", new Object[]{"a,b", "c\"d", "e\nf"})
                .build();

        Path tempFile = Files.createTempFile("test_df_escape", ".csv");
        try {
            df.toCsv(tempFile.toString());

            String content = Files.readString(tempFile);

            String normalized = content.replace("\r\n", "\n");

            assertTrue(normalized.contains("\"a,b\""));
            assertTrue(normalized.contains("\"c\"\"d\""));
            assertTrue(normalized.contains("\"e\nf\""));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
