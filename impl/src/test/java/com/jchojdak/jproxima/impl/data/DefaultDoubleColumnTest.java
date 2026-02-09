package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.DoubleColumn;
import com.jchojdak.jproxima.data.DoubleColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDoubleColumnTest {

    private static final String NAME = "testColumn";

    private DoubleColumn column(double... values) {
        return DoubleColumnBuilder.init()
                .name(NAME)
                .add(values)
                .build();
    }

    private DoubleColumn columnWithNulls() {
        return DoubleColumnBuilder.init()
                .name(NAME)
                .add(10.0)
                .addNull()
                .add(20.0)
                .build();
    }

    @Test
    void shouldReturnValueAtSpecifiedIndex() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(20.0, column.getDouble(1));
    }

    @Test
    void shouldReturnFirstElement() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(10.0, column.getDouble(0));
    }

    @Test
    void shouldReturnLastElement() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(30.0, column.getDouble(2));
    }

    @Test
    void shouldReturnCorrectSize() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnCorrectType() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(DataType.DOUBLE, column.getType());
    }

    @Test
    void shouldReturnCorrectName() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        assertEquals(NAME, column.getName());
    }

    @Test
    void shouldDetectNullValue() {
        DoubleColumn column = columnWithNulls();

        assertFalse(column.isNull(0));
        assertTrue(column.isNull(1));
        assertFalse(column.isNull(2));
    }

    @Test
    void shouldReturnNullFromGenericGet() {
        DoubleColumn column = DoubleColumnBuilder.init()
                .addNull()
                .build();

        assertTrue(column.isNull(0));
    }

    @Test
    void shouldThrowNullPointerExceptionOnGetDoubleWhenNull() {
        DoubleColumn column = columnWithNulls();

        assertThrows(NullPointerException.class, () -> column.getDouble(1));
    }

    @Test
    void shouldReturnCopyInToDoubleArray() {
        double[] data = {10.0, 20.0, 30.0};
        DoubleColumn column = column(data);

        double[] array = column.toDoubleArray();

        assertArrayEquals(data, array);
    }

    @Test
    void shouldReturnCopyInToArray() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        Object[] array = column.toArray();
        Object[] expected = {10.0, 20.0, 30.0};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToDoubleArray() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        double[] array = column.toDoubleArray();
        array[0] = 997.0;

        assertEquals(10.0, column.getDouble(0));
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToArray() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        Object[] array = column.toArray();
        array[0] = 997.0;

        assertEquals(10.0, column.getDouble(0));
    }

    @Test
    void shouldConvertNullsInToArray() {
        DoubleColumn column = columnWithNulls();

        Object[] array = column.toArray();
        Object[] expected = {10.0, null, 20.0};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldFormatToStringWithDefaultLimit() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        String expected = "testColumn [DOUBLE]: 10.0, 20.0, 30.0";
        String result = column.toString();

        assertEquals(expected, result);
    }

    @Test
    void shouldFormatStringWithCustomLimitLessThanSize() {
        DoubleColumn column = column(10.0, 20.0, 30.0, 40.0, 50.0);

        String expected = "testColumn [DOUBLE]: 10.0, 20.0, 30.0, ...";
        String result = column.toString(3);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringWithCustomLimitGreaterThanSize() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        String expected = "testColumn [DOUBLE]: 10.0, 20.0, 30.0";
        String result = column.toString(10);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnBoxedDoubleFromGenericGet() {
        DoubleColumn column = column(10.0, 20.0, 30.0);

        Object result = column.get(0);

        assertEquals(10.0, result);
        assertInstanceOf(Double.class, result);
    }
}