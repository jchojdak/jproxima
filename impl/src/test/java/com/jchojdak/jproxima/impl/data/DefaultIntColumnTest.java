package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.IntColumn;
import com.jchojdak.jproxima.data.IntColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultIntColumnTest {

    private static final String NAME = "testColumn";

    private IntColumn column(int... values) {
        return IntColumnBuilder.init()
                .name(NAME)
                .add(values)
                .build();
    }

    private IntColumn columnWithNulls() {
        return IntColumnBuilder.init()
                .name(NAME)
                .add(10)
                .addNull()
                .add(20)
                .build();
    }

    @Test
    void shouldReturnValueAtSpecifiedIndex() {
        IntColumn column = column(10, 20, 30);

        assertEquals(20, column.getInt(1));
    }

    @Test
    void shouldReturnFirstElement() {
        IntColumn column = column(10, 20, 30);

        assertEquals(10, column.getInt(0));
    }

    @Test
    void shouldReturnLastElement() {
        IntColumn column = column(10, 20, 30);

        assertEquals(30, column.getInt(2));
    }

    @Test
    void shouldReturnCorrectSize() {
        IntColumn column = column(10, 20, 30);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnCorrectType() {
        IntColumn column = column(10, 20, 30);

        assertEquals(DataType.INTEGER, column.getType());
    }

    @Test
    void shouldReturnCorrectName() {
        IntColumn column = column(10, 20, 30);

        assertEquals(NAME, column.getName());
    }

    @Test
    void shouldDetectNullValue() {
        IntColumn column = columnWithNulls();

        assertFalse(column.isNull(0));
        assertTrue(column.isNull(1));
        assertFalse(column.isNull(2));
    }

    @Test
    void shouldReturnNullFromGenericGet() {
        IntColumn column = IntColumnBuilder.init()
                .addNull()
                .build();

        assertTrue(column.isNull(0));
    }

    @Test
    void shouldThrowNullPointerExceptionOnGetIntWhenNull() {
        IntColumn column = columnWithNulls();

        assertThrows(NullPointerException.class, () -> column.getInt(1));
    }

    @Test
    void shouldReturnCopyInToIntArray() {
        int[] data = {10, 20, 30};
        IntColumn column = column(data);

        int[] array = column.toIntArray();

        assertArrayEquals(data, array);
    }

    @Test
    void shouldReturnCopyInToArray() {
        IntColumn column = column(10, 20, 30);

        Object[] array = column.toArray();
        Object[] expected = {10, 20, 30};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToIntArray() {
        IntColumn column = column(10, 20, 30);

        int[] array = column.toIntArray();
        array[0] = 997;

        assertEquals(10, column.getInt(0));
    }


    @Test
    void shouldNotAffectColumnWhenModifyingToArray() {
        IntColumn column = column(10, 20, 30);

        Object[] array = column.toArray();
        array[0] = 997;

        assertEquals(10, column.getInt(0));
    }

    @Test void shouldConvertNullsInToArray() {
        IntColumn column = columnWithNulls();

        Object[] array = column.toArray();
        Object[] expected = {10, null, 20};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldFormatToStringWithDefaultLimit() {
        IntColumn column = column(10, 20, 30);

        String expected = "testColumn [INTEGER]: 10, 20, 30";
        String result = column.toString();

        assertEquals(expected, result);
    }

    @Test
    void shouldFormatStringWithCustomLimitLessThanSize() {
        IntColumn column = column(10, 20, 30, 40, 50);

        String expected = "testColumn [INTEGER]: 10, 20, 30, ...";
        String result = column.toString(3);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringWithCustomLimitGreaterThanSize() {
        IntColumn column = column(10, 20, 30);

        String expected = "testColumn [INTEGER]: 10, 20, 30";
        String result = column.toString(10);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnBoxedIntegerFromGenericGet() {
        IntColumn column = column(10, 20, 30);

        Object result = column.get(0);

        assertEquals(10, result);
        assertInstanceOf(Integer.class, result);
    }
}
