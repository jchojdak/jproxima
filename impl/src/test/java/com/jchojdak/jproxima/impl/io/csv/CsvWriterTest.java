package com.jchojdak.jproxima.impl.io.csv;

import com.jchojdak.jproxima.data.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvWriterTest {

    @TempDir
    Path tempDir;

    private List<Column> generateColumns() {
        Column id = IntColumnBuilder.init()
                .name("id")
                .add(new int[]{1, 2, 3})
                .build();

        Column name = StringColumnBuilder.init()
                .name("name")
                .add(new String[]{"Alice", "Bob", "Jacob"})
                .build();

        Column active = BooleanColumnBuilder.init()
                .name("active")
                .add(new boolean[]{true, false, true})
                .build();

        Column score = DoubleColumnBuilder.init()
                .name("score")
                .add(new double[]{10.5, 20.0, 15.5})
                .build();

        return List.of(id, name, active, score);
    }

    @Test
    void shouldWriteToCsv() throws IOException {
        Path output = tempDir.resolve("test_df.csv");

        CsvWriter.write(output.toString(), generateColumns());

        List<String> lines = Files.readAllLines(output);

        assertAll(
                () -> assertEquals(4, lines.size()),
                () -> assertEquals("id,name,active,score", lines.getFirst()),
                () -> assertEquals("1,Alice,true,10.5", lines.get(1)),
                () -> assertEquals("2,Bob,false,20.0", lines.get(2)),
                () -> assertEquals("3,Jacob,true,15.5", lines.get(3))
        );
    }

    @Test
    void shouldEscapeSpecialCharactersInCsv() throws IOException {
        Path output = tempDir.resolve("test_df_escape.csv");

        Column text = StringColumnBuilder.init()
                .name("text")
                .add(new String[]{"a,b", "c\"d", "e\nf"})
                .build();

        CsvWriter.write(output.toString(), List.of(text));

        String content = Files.readString(output);
        String normalized = content.replace("\r\n", "\n");

        assertAll(
                () -> assertTrue(normalized.contains("\"a,b\"")),
                () -> assertTrue(normalized.contains("\"c\"\"d\"")),
                () -> assertTrue(normalized.contains("\"e\nf\""))
        );
    }

    @Test
    void shouldThrowExceptionWhenPathIsInvalid() {
        Path invalidPath = Path.of("/this/path/does/not/exist/test.csv");

        Exception exception = assertThrows(
                CsvWriteException.class,
                () -> CsvWriter.write(invalidPath, generateColumns())
        );

        assertTrue(exception.getMessage().contains("Error writing CSV file"));
    }
}