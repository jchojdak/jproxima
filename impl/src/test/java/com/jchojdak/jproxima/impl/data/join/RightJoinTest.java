package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RightJoinTest {

    @Test
    void shouldReturnAllRightRowsWithNullForUnmatchedLeft() {
        // LEFT:          RIGHT:
        // id | name      id | score
        //  1 | Alice      1 | 90
        //  2 | Bob        3 | 85
        //  3 | Carol      4 | 70

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2, 3})
                .addColumn("name", new Object[]{"Alice", "Bob", "Carol"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 3, 4})
                .addColumn("score", new Object[]{90, 85, 70})
                .build();

        DataFrame result = new RightJoin().join(
                left,
                right,
                List.of("id"),
                List.of("id"),
                "_x",
                "_y"
        );

        assertNotNull(result);
        assertEquals(3, result.rowCount());
        assertEquals("Alice", result.getColumn("name").get(0));
        assertEquals("Carol", result.getColumn("name").get(1));
        assertTrue(result.getColumn("name").isNull(2));
    }
}
