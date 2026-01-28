package com.jchojdak.jproxima.impl.io.type;

import com.jchojdak.jproxima.data.DataType;

import java.util.List;

/**
 * Converts string values to specific data types.
 */
public final class ValueConverter {

    private final String nullValue;

    public ValueConverter(String nullValue) {
        this.nullValue = nullValue;
    }

    /**
     * Converts a string value to the specified data type.
     * Returns null if the value represents null.
     * Falls back to string if conversion fails.
     */
    public Object convert(String value, DataType type) {
        if (value == null || value.equals(nullValue)) {
            return null;
        }

        try {
            return switch (type) {
                case INTEGER -> Integer.parseInt(value);
                case DOUBLE -> Double.parseDouble(value);
                case BOOLEAN -> Boolean.parseBoolean(value);
                case STRING -> value;
            };
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * Converts a list of string values to the specified data type.
     */
    public Object[] convertAll(List<String> data, DataType type) {
        Object[] result = new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = convert(data.get(i), type);
        }
        return result;
    }
}
