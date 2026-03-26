package com.jchojdak.jproxima.impl.io.excel;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExcelWriterTest {

    @TempDir
    Path tempDir;

    private DataFrame generateDataFrame() {
        return DataFrameBuilder.create()
                .addColumn("id", new Object[]{1, 2, 3})
                .addColumn("name", new Object[]{"Alice", "Bob", "Jacob"})
                .addColumn("active", new Object[]{true, false, true})
                .addColumn("score", new Object[]{10.5, 20.0, 15.5})
                .build();
    }

    @Test
    void shouldWriteDataFrameToExcelFile() {
        Path output = tempDir.resolve("basic_test.xlsx");

        DataFrame df = generateDataFrame();

        ExcelWriter.write(output, df);

        assertAll(
                () -> assertTrue(Files.exists(output)),
                () -> assertTrue(Files.size(output) > 0)
        );
    }

    @Test
    void shouldHandleEmptyDataFrame() {
        Path output = tempDir.resolve("empty_test.xlsx");

        DataFrame df = DataFrameBuilder.create().build();

        ExcelWriter.write(output, df);

        assertFalse(Files.exists(output));
    }

    @Test
    void shouldThrowExceptionWhenPathIsInvalid() {
        Path output = tempDir.resolve("invalid_path");
        assertDoesNotThrow(() -> Files.createDirectory(output));

        DataFrame df = generateDataFrame();

        assertThrows(ExcelWriteException.class, () -> ExcelWriter.write(output, df));
    }

    @Test
    void shouldWriteUsingStringPath() {
        Path output = tempDir.resolve("string_path_test.xlsx");
        String pathString = output.toAbsolutePath().toString();

        DataFrame df = generateDataFrame();

        ExcelWriter.write(pathString, df);

        assertTrue(Files.exists(output));
    }
}