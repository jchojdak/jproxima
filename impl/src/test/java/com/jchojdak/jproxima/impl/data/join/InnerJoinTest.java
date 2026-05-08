package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InnerJoinTest {

    @Test
    void shouldReturnOnlyMatchingRowsWithDataFromBothSides() {
        // LEFT:          RIGHT:
        // id | name      id | score
        //  1 | Alice      1 | 90
        //  2 | Bob        3 | 85
        //  3 | Carol

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2, 3})
                .addColumn("name", new Object[]{"Alice", "Bob", "Carol"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 3})
                .addColumn("score", new Object[]{90, 85})
                .build();

        DataFrame result = new InnerJoin().join(
                left,
                right,
                List.of("id"),
                List.of("id"),
                "_x",
                "_y"
        );

        assertNotNull(result);
        assertEquals(2, result.rowCount());
        assertEquals(1, result.getColumn("id").get(0));
        assertEquals("Alice", result.getColumn("name").get(0));
        assertEquals(90, result.getColumn("score").get(0));
        assertEquals(3, result.getColumn("id").get(1));
        assertEquals("Carol", result.getColumn("name").get(1));
        assertEquals(85, result.getColumn("score").get(1));
    }
}