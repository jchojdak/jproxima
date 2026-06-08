package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeftJoinTest {

    @Test
    void shouldJoinOnSingleKey() {
        // LEFT:
        // id | name
        //  1 | Alice
        //  2 | Bob
        //  3 | Carol

        // RIGHT:
        // id | score
        //  1 | 90
        //  3 | 85

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2, 3})
                .addColumn("name", new Object[]{"Alice", "Bob", "Carol"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 3})
                .addColumn("score", new Object[]{90, 85})
                .build();

        DataFrame result = new LeftJoin().join(
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
                () -> assertEquals(1, result.getColumn("id").get(0)),
                () -> assertEquals("Alice", result.getColumn("name").get(0)),
                () -> assertEquals(90, result.getColumn("score").get(0)),

                // row 1 -> 2 + Bob + null
                () -> assertEquals(2, result.getColumn("id").get(1)),
                () -> assertEquals("Bob", result.getColumn("name").get(1)),
                () -> assertTrue(result.getColumn("score").isNull(1)),

                // row 2 -> 3 + Carol + 85
                () -> assertEquals(3, result.getColumn("id").get(2)),
                () -> assertEquals("Carol", result.getColumn("name").get(2)),
                () -> assertEquals(85, result.getColumn("score").get(2))
        );
    }

    @Test
    void shouldJoinOnMultipleKeys() {
        // LEFT:
        // id | country | name
        // 1  | PL      | Alice
        // 2  | DE      | Bob

        // RIGHT:
        // id | country | score
        // 1  | PL      | 90
        // 2  | DE      | 80

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2})
                .addColumn("country", new Object[]{"PL", "DE"})
                .addColumn("name", new Object[]{"Alice", "Bob"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2})
                .addColumn("country", new Object[]{"PL", "PL"})
                .addColumn("score", new Object[]{90, 80})
                .build();

        DataFrame result = new LeftJoin().join(
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
                () -> assertEquals(1, result.getColumn("id").get(0)),
                () -> assertEquals("PL", result.getColumn("country").get(0)),
                () -> assertEquals("Alice", result.getColumn("name").get(0)),
                () -> assertEquals(90, result.getColumn("score").get(0)),

                // row 1 -> Bob + DE + null
                () -> assertEquals(2, result.getColumn("id").get(1)),
                () -> assertEquals("DE", result.getColumn("country").get(1)),
                () -> assertEquals("Bob", result.getColumn("name").get(1)),
                () -> assertTrue(result.getColumn("score").isNull(1))
        );
    }
}
