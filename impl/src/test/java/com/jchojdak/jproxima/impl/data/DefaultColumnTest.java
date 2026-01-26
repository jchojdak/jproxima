package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.ColumnBuilder;
import com.jchojdak.jproxima.data.DataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultColumnTest {

    @Test
    void shouldReturnValueAtSpecifiedIndex() {
        String[] data = {"value1", "value2", "value3"};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.STRING)
                .addAll(data)
                .build();

        assertEquals("value2", column.get(1));
    }

    @Test
    void shouldReturnFirstElement() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        assertEquals(1, column.get(0));
    }

    @Test
    void shouldReturnLastElement() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        assertEquals(3, column.get(2));
    }

    @Test
    void shouldReturnCorrectNumberOfRows() {
        Integer[] data = {1, 2, 3, 4, 5};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        assertEquals(5, column.size());
    }

    @Test
    void shouldReturnZeroForEmptyColumn() {
        String[] data = {};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.STRING)
                .addAll(data)
                .build();

        assertEquals(0, column.size());
    }

    @Test
    void shouldReturnCorrectDataType() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        assertEquals(DataType.INTEGER, column.getType());
    }

    @Test
    void shouldReturnArrayWithSameValues() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        Object[] result = column.toArray();

        assertArrayEquals(data, result);
    }

    @Test
    void shouldReturnCorrectName() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("testColumn")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        assertEquals("testColumn", column.getName());
    }

    @Test
    void shouldReturnStringWithDefaultLimit() {
        Integer[] data = {1, 2, 3, 4, 5};
        Column column = ColumnBuilder.create()
                .name("numbers")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        String expected = "numbers [INTEGER]: 1, 2, 3, 4, 5";
        assertEquals(expected, column.toString());
    }

    @Test
    void shouldReturnStringWithCustomLimitLessThanSize() {
        Integer[] data = {1, 2, 3, 4, 5};
        Column column = ColumnBuilder.create()
                .name("numbers")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        String expected = "numbers [INTEGER]: 1, 2, 3, ...";
        assertEquals(expected, column.toString(3));
    }

    @Test
    void shouldReturnStringWithCustomLimitGreaterThanSize() {
        Integer[] data = {1, 2, 3};
        Column column = ColumnBuilder.create()
                .name("numbers")
                .type(DataType.INTEGER)
                .addAll(data)
                .build();

        String expected = "numbers [INTEGER]: 1, 2, 3";
        assertEquals(expected, column.toString(10));
    }
}