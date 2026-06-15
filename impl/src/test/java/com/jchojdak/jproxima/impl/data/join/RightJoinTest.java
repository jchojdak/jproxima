package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RightJoinTest {

    @Test
    void shouldReturnAllRightRowsWithNullForUnmatchedLeft() {
        // LEFT:
        // id | name
        //  1 | Alice
        //  2 | Bob
        //  3 | Carol

        // RIGHT:
        // id | score
        //  1 | 90
        //  3 | 85
        //  4 | 70

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

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(3, result.rowCount()),

                // row 0 -> 1 + Alice + 90
                () -> assertEquals("Alice", result.getColumn("name").get(0)),
                () -> assertEquals(90, result.getColumn("score").get(0)),

                // row 1 -> 3 + Carol + 85
                () -> assertEquals("Carol", result.getColumn("name").get(1)),
                () -> assertEquals(85, result.getColumn("score").get(1)),

                // row 2 -> 4 + null + 70
                () -> assertTrue(result.getColumn("name").isNull(2)),
                () -> assertEquals(70, result.getColumn("score").get(2))
        );
    }

    @Test
    void shouldJoinOnMultipleKeys() {
        // LEFT:
        // id | country | name
        // 1  | PL      | Alice
        // 2  | PL      | Bob

        // RIGHT:
        // id | country | score
        // 1  | PL      | 90
        // 2  | DE      | 80

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2})
                .addColumn("country", new Object[]{"PL", "PL"})
                .addColumn("name", new Object[]{"Alice", "Bob"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2})
                .addColumn("country", new Object[]{"PL", "DE"})
                .addColumn("score", new Object[]{90, 80})
                .build();

        DataFrame result = new RightJoin().join(
                left,
                right,
                List.of("id", "country"),
                List.of("id", "country"),
                "_x",
                "_y"
        );

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.rowCount()),

                // row 0 -> Alice + PL + 90
                () -> assertEquals("Alice", result.getColumn("name").get(0)),
                () -> assertEquals(90, result.getColumn("score").get(0)),

                // row 1 -> null + DE + 80
                () -> assertTrue(result.getColumn("name").isNull(1)),
                () -> assertEquals(80, result.getColumn("score").get(1))
        );
    }
}
