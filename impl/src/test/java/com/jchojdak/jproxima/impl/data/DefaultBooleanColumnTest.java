package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.BooleanColumn;
import com.jchojdak.jproxima.data.BooleanColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultBooleanColumnTest {

    private static final String NAME = "testColumn";

    private BooleanColumn column(boolean... values) {
        return BooleanColumnBuilder.init()
                .name(NAME)
                .add(values)
                .build();
    }

    private BooleanColumn columnWithNulls() {
        return BooleanColumnBuilder.init()
                .name(NAME)
                .add(true)
                .addNull()
                .add(false)
                .build();
    }

    @Test
    void shouldReturnValueAtSpecifiedIndex() {
        BooleanColumn column = column(true, false, true);

        assertFalse(column.getBoolean(1));
    }

    @Test
    void shouldReturnFirstElement() {
        BooleanColumn column = column(true, false, true);

        assertTrue(column.getBoolean(0));
    }

    @Test
    void shouldReturnLastElement() {
        BooleanColumn column = column(true, false, true);

        assertTrue(column.getBoolean(2));
    }

    @Test
    void shouldReturnCorrectSize() {
        BooleanColumn column = column(true, false, true);

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnCorrectType() {
        BooleanColumn column = column(true, false, true);

        assertEquals(DataType.BOOLEAN, column.getType());
    }

    @Test
    void shouldReturnCorrectName() {
        BooleanColumn column = column(true, false, true);

        assertEquals(NAME, column.getName());
    }

    @Test
    void shouldDetectNullValue() {
        BooleanColumn column = columnWithNulls();

        assertFalse(column.isNull(0));
        assertTrue(column.isNull(1));
        assertFalse(column.isNull(2));
    }

    @Test
    void shouldReturnNullFromGenericGet() {
        BooleanColumn column = BooleanColumnBuilder.init()
                .addNull()
                .build();

        assertTrue(column.isNull(0));
    }

    @Test
    void shouldThrowNullPointerExceptionOnGetBooleanWhenNull() {
        BooleanColumn column = columnWithNulls();

        assertThrows(NullPointerException.class, () -> column.getBoolean(1));
    }

    @Test
    void shouldReturnCopyInToBooleanArray() {
        boolean[] data = {true, false, true};
        BooleanColumn column = column(data);

        boolean[] array = column.toBooleanArray();

        assertArrayEquals(data, array);
    }

    @Test
    void shouldReturnCopyInToArray() {
        BooleanColumn column = column(true, false, true);

        Object[] array = column.toArray();
        Object[] expected = {true, false, true};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToBooleanArray() {
        BooleanColumn column = column(true, false, true);

        boolean[] array = column.toBooleanArray();
        array[0] = false;

        assertTrue(column.getBoolean(0));
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToArray() {
        BooleanColumn column = column(true, false, true);

        Object[] array = column.toArray();
        array[0] = false;

        assertTrue(column.getBoolean(0));
    }

    @Test
    void shouldConvertNullsInToArray() {
        BooleanColumn column = columnWithNulls();

        Object[] array = column.toArray();
        Object[] expected = {true, null, false};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldFormatToStringWithDefaultLimit() {
        BooleanColumn column = column(true, false, true);

        String expected = "testColumn [BOOLEAN]: true, false, true";
        String result = column.toString();

        assertEquals(expected, result);
    }

    @Test
    void shouldFormatStringWithCustomLimitLessThanSize() {
        BooleanColumn column = column(true, false, true, false, true);

        String expected = "testColumn [BOOLEAN]: true, false, true, ...";
        String result = column.toString(3);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringWithCustomLimitGreaterThanSize() {
        BooleanColumn column = column(true, false, true);

        String expected = "testColumn [BOOLEAN]: true, false, true";
        String result = column.toString(10);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnBoxedBooleanFromGenericGet() {
        BooleanColumn column = column(true, false, true);

        Object result = column.get(0);

        assertEquals(true, result);
        assertInstanceOf(Boolean.class, result);
    }
}