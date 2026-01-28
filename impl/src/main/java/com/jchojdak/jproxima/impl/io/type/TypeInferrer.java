package com.jchojdak.jproxima.impl.io.type;

import com.jchojdak.jproxima.data.DataType;

import java.util.List;

/**
 * Infers data types from string values.
 */
public final class TypeInferrer {

    private final String nullValue;

    public TypeInferrer(String nullValue) {
        this.nullValue = nullValue;
    }

    /**
     * Infers the data type from a list of string values.
     * Returns the first detected type or STRING if all values are null.
     */
    public DataType inferType(List<String> data) {
        for (String value : data) {
            if (isNull(value)) {
                continue;
            }

            if (isInteger(value)) {
                return DataType.INTEGER;
            }

            if (isDouble(value)) {
                return DataType.DOUBLE;
            }

            if (isBoolean(value)) {
                return DataType.BOOLEAN;
            }

            return DataType.STRING;
        }

        return DataType.STRING;
    }

    private boolean isNull(String value) {
        return value == null || value.equals(nullValue);
    }

    private boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }
}