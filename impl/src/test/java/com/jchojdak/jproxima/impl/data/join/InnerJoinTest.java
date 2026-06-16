package com.jchojdak.jproxima.impl.data.join;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InnerJoinTest {

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

        DataFrame result = new InnerJoin().join(
                left,
                right,
                List.of("id"),
                List.of("id"),
                "_x",
                "_y"
        );

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.rowCount()),

                // row 0 -> 1 + Alice + 90
                () -> assertEquals(1, result.getColumn("id").get(0)),
                () -> assertEquals("Alice", result.getColumn("name").get(0)),
                () -> assertEquals(90, result.getColumn("score").get(0)),

                // row 1 -> 3 + Carol + 85
                () -> assertEquals(3, result.getColumn("id").get(1)),
                () -> assertEquals("Carol", result.getColumn("name").get(1)),
                () -> assertEquals(85, result.getColumn("score").get(1))
        );
    }

    @Test
    void shouldJoinOnMultipleKeys() {
        // LEFT:
        // id | country | name
        // 1  | PL      | Alice
        // 1  | DE      | Bob
        // 2  | PL      | Carol

        // RIGHT:
        // id | country | score
        // 1  | PL      | 90
        // 1  | DE      | 80
        // 2  | DE      | 70

        DataFrame left = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 1, 2})
                .addColumn("country", new Object[]{"PL", "DE", "PL"})
                .addColumn("name", new Object[]{"Alice", "Bob", "Carol"})
                .build();

        DataFrame right = DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 1, 2})
                .addColumn("country", new Object[]{"PL", "DE", "DE"})
                .addColumn("score", new Object[]{90, 80, 70})
                .build();

        DataFrame result = new InnerJoin().join(
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

                // row 1 -> Bob + DE + 80
                () -> assertEquals(1, result.getColumn("id").get(1)),
                () -> assertEquals("DE", result.getColumn("country").get(1)),
                () -> assertEquals("Bob", result.getColumn("name").get(1)),
                () -> assertEquals(80, result.getColumn("score").get(1))
        );
    }
}