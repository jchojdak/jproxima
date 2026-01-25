package com.jchojdak.jproxima.data;

/**
 * Defines the supported data types for DataFrame columns.
 */
public enum DataType {
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    STRING(String.class),
    BOOLEAN(Boolean.class);

    private final Class<?> javaType;

    DataType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public boolean isValid(Object value) {
        return value == null || javaType.isInstance(value);
    }

    public static DataType from(Object[] data) {
        for (Object value : data) {
            if (value != null) {
                return from(value);
            }
        }
        throw new IllegalArgumentException("Cannot infer DataType from column with only null values");
    }

    public static DataType from(Object value) {
        for (DataType type : values()) {
            if (type.isValid(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported data type: " + value.getClass());
    }
}