package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import com.jchojdak.jproxima.data.join.JoinType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameJoinTest {

    private DataFrame df(Object... columns) {
        DataFrameBuilder builder = DataFrameBuilder.create();

        for (int i = 0; i < columns.length; i += 2) {
            String name = (String) columns[i];
            Object[] data = (Object[]) columns[i + 1];
            builder.addColumn(name, data);
        }

        return builder.build();
    }

    @Test
    void innerJoin_shouldReturnOnlyMatchingRows() {
        DataFrame left = df(
                "id", new Object[]{1, 2, 3},
                "name", new Object[]{"A", "B", "C"}
        );

        DataFrame right = df(
                "id", new Object[]{2, 3, 4},
                "age", new Object[]{20, 30, 40}
        );

        DataFrame result = left.join(right, JoinType.INNER, "id");

        assertEquals(2, result.rowCount());

        Object[][] data = result.toArray();

        assertAll(
                () -> assertArrayEquals(new Object[]{2, "B", 20}, data[0]),
                () -> assertArrayEquals(new Object[]{3, "C", 30}, data[1])
        );
    }

    @Test
    void leftJoin_shouldKeepAllLeftRows() {
        DataFrame left = df(
                "id", new Object[]{1, 2, 3},
                "name", new Object[]{"A", "B", "C"}
        );

        DataFrame right = df(
                "id", new Object[]{2},
                "age", new Object[]{20}
        );

        DataFrame result = left.join(right, JoinType.LEFT, "id");

        assertEquals(3, result.rowCount());

        Object[][] data = result.toArray();

        assertAll(
                () -> assertArrayEquals(new Object[]{1, "A", null}, data[0]),
                () -> assertArrayEquals(new Object[]{2, "B", 20}, data[1]),
                () -> assertArrayEquals(new Object[]{3, "C", null}, data[2])
        );
    }

    @Test
    void rightJoin_shouldKeepAllRightRows() {
        DataFrame left = df(
                "id", new Object[]{2},
                "name", new Object[]{"B"}
        );

        DataFrame right = df(
                "id", new Object[]{1, 2, 3},
                "age", new Object[]{10, 20, 30}
        );

        DataFrame result = left.join(right, JoinType.RIGHT, "id");

        assertEquals(3, result.rowCount());

        Object[][] data = result.toArray();

        assertAll(
                () -> assertArrayEquals(new Object[]{null, 10}, data[0]),
                () -> assertArrayEquals(new Object[]{"B", 20}, data[1]),
                () -> assertArrayEquals(new Object[]{null, 30}, data[2])
        );
    }

    @Test
    void fullJoin_shouldReturnUnionOfKeys() {
        DataFrame left = df(
                "id", new Object[]{1, 2},
                "name", new Object[]{"A", "B"}
        );

        DataFrame right = df(
                "id", new Object[]{2, 3},
                "age", new Object[]{20, 30}
        );

        DataFrame result = left.join(right, JoinType.FULL, "id");

        assertEquals(3, result.rowCount());

        Object[][] data = result.toArray();

        assertAll(
                () -> assertArrayEquals(new Object[]{1, "A", null}, data[0]),
                () -> assertArrayEquals(new Object[]{2, "B", 20}, data[1]),
                () -> assertArrayEquals(new Object[]{3, null, 30}, data[2])
        );
    }

}

