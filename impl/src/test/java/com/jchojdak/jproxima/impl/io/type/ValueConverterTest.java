package com.jchojdak.jproxima.impl.io.type;

import com.jchojdak.jproxima.data.DataType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueConverterTest {

    private static final String NULL_VALUE = "NULL";
    private final ValueConverter converter = new ValueConverter(NULL_VALUE);

    @ParameterizedTest
    @CsvSource({
            "777,INTEGER,777",
            "1.234,DOUBLE,1.234",
            "true,BOOLEAN,true",
            "false,BOOLEAN,false",
            "i love pizza,STRING,i love pizza",
            "NULL,STRING,"
    })
    void shouldConvertCorrectly(String input, String type, String expected) {
        DataType dataType = DataType.valueOf(type);
        Object expectedValue;

        switch (dataType) {
            case INTEGER -> expectedValue = Integer.parseInt(expected);
            case DOUBLE -> expectedValue = Double.parseDouble(expected);
            case BOOLEAN -> expectedValue = Boolean.parseBoolean(expected);
            case STRING -> expectedValue = expected;
            default -> throw new IllegalStateException("Unexpected type: " + dataType);
        }

        assertEquals(expectedValue, converter.convert(input, dataType));
    }

    @Test
    void shouldFallbackToStringWhenIntegerConversionFails() {
        String invalidInteger = "not_a_number";
        Object result = converter.convert(invalidInteger, DataType.INTEGER);

        assertEquals(invalidInteger, result);
    }

    @Test
    void shouldFallbackToStringWhenDoubleConversionFails() {
        String invalidDouble = "abc123";
        Object result = converter.convert(invalidDouble, DataType.DOUBLE);

        assertEquals(invalidDouble, result);
    }

    @Test
    void shouldConvertAllWithFallbackForInvalidIntegers() {
        List<String> input = Arrays.asList("1", "invalid", "3", "also_invalid");
        Object[] result = converter.convertAll(input, DataType.INTEGER);

        Object[] expected = new Object[]{1, "invalid", 3, "also_invalid"};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldConvertAllWithFallbackForInvalidDoubles() {
        List<String> input = Arrays.asList("1.5", "not_a_number", "2.7");
        Object[] result = converter.convertAll(input, DataType.DOUBLE);

        Object[] expected = new Object[]{1.5, "not_a_number", 2.7};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldConvertAllIntegers() {
        List<String> input = Arrays.asList("1", "2", "3", "NULL", "5");
        Object[] result = converter.convertAll(input, DataType.INTEGER);

        Object[] expected = new Object[]{1, 2, 3, null, 5};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldConvertAllDoubles() {
        List<String> input = Arrays.asList("1.11", "2.22", "NULL", "3.33");
        Object[] result = converter.convertAll(input, DataType.DOUBLE);

        Object[] expected = new Object[]{1.11, 2.22, null, 3.33};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldConvertAllBooleans() {
        List<String> input = Arrays.asList("true", "false", "NULL", "true");
        Object[] result = converter.convertAll(input, DataType.BOOLEAN);

        Object[] expected = new Object[]{true, false, null, true};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldConvertAllStrings() {
        List<String> input = Arrays.asList("i", "love", "NULL", "pizza");
        Object[] result = converter.convertAll(input, DataType.STRING);

        Object[] expected = new Object[]{"i", "love", null, "pizza"};
        assertArrayEquals(expected, result);
    }

    @Test
    void shouldHandleEmptyList() {
        List<String> input = List.of();
        Object[] result = converter.convertAll(input, DataType.INTEGER);

        assertEquals(0, result.length);
    }

}
