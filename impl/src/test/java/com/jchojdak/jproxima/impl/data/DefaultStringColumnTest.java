package com.jchojdak.jproxima.impl.data;

import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.data.StringColumn;
import com.jchojdak.jproxima.data.StringColumnBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultStringColumnTest {

    private static final String NAME = "testColumn";

    private StringColumn column(String... values) {
        return StringColumnBuilder.init()
                .name(NAME)
                .add(values)
                .build();
    }

    private StringColumn columnWithNulls() {
        return StringColumnBuilder.init()
                .name(NAME)
                .add("first")
                .add((String) null)
                .add("third")
                .build();
    }

    @Test
    void shouldReturnValueAtSpecifiedIndex() {
        StringColumn column = column("first", "second", "third");

        assertEquals("second", column.getString(1));
    }

    @Test
    void shouldReturnFirstElement() {
        StringColumn column = column("first", "second", "third");

        assertEquals("first", column.getString(0));
    }

    @Test
    void shouldReturnLastElement() {
        StringColumn column = column("first", "second", "third");

        assertEquals("third", column.getString(2));
    }

    @Test
    void shouldReturnCorrectSize() {
        StringColumn column = column("first", "second", "third");

        assertEquals(3, column.size());
    }

    @Test
    void shouldReturnCorrectType() {
        StringColumn column = column("first", "second", "third");

        assertEquals(DataType.STRING, column.getType());
    }

    @Test
    void shouldReturnCorrectName() {
        StringColumn column = column("first", "second", "third");

        assertEquals(NAME, column.getName());
    }

    @Test
    void shouldDetectNullValue() {
        StringColumn column = columnWithNulls();

        assertFalse(column.isNull(0));
        assertTrue(column.isNull(1));
        assertFalse(column.isNull(2));
    }

    @Test
    void shouldReturnNullFromGenericGet() {
        StringColumn column = StringColumnBuilder.init()
                .add((String) null)
                .build();

        assertTrue(column.isNull(0));
    }

    @Test
    void shouldReturnNullFromGetStringWhenNull() {
        StringColumn column = columnWithNulls();

        assertNull(column.getString(1));
    }

    @Test
    void shouldReturnCopyInToStringArray() {
        String[] data = {"first", "second", "third"};
        StringColumn column = column(data);

        String[] array = column.toStringArray();

        assertArrayEquals(data, array);
    }

    @Test
    void shouldReturnCopyInToArray() {
        StringColumn column = column("first", "second", "third");

        Object[] array = column.toArray();
        Object[] expected = {"first", "second", "third"};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToStringArray() {
        StringColumn column = column("first", "second", "third");

        String[] array = column.toStringArray();
        array[0] = "modified";

        assertEquals("first", column.getString(0));
    }

    @Test
    void shouldNotAffectColumnWhenModifyingToArray() {
        StringColumn column = column("first", "second", "third");

        Object[] array = column.toArray();
        array[0] = "modified";

        assertEquals("first", column.getString(0));
    }

    @Test
    void shouldConvertNullsInToArray() {
        StringColumn column = columnWithNulls();

        Object[] array = column.toArray();
        Object[] expected = {"first", null, "third"};

        assertArrayEquals(expected, array);
    }

    @Test
    void shouldFormatToStringWithDefaultLimit() {
        StringColumn column = column("first", "second", "third");

        String expected = "testColumn [STRING]: first, second, third";
        String result = column.toString();

        assertEquals(expected, result);
    }

    @Test
    void shouldFormatStringWithCustomLimitLessThanSize() {
        StringColumn column = column("first", "second", "third", "fourth", "fifth");

        String expected = "testColumn [STRING]: first, second, third, ...";
        String result = column.toString(3);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringWithCustomLimitGreaterThanSize() {
        StringColumn column = column("first", "second", "third");

        String expected = "testColumn [STRING]: first, second, third";
        String result = column.toString(10);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnStringFromGenericGet() {
        StringColumn column = column("first", "second", "third");

        Object result = column.get(0);

        assertEquals("first", result);
        assertInstanceOf(String.class, result);
    }
}