package com.jchojdak.jproxima.impl.io.excel;

import com.jchojdak.jproxima.data.DataFrame;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ExcelParserTest {

    @SuppressWarnings("SameParameterValue")
    private Path getExcelPath(String fileName) throws URISyntaxException {
        return Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("excel/" + fileName)).toURI()
        );
    }

    @Test
    void shouldParseExcelFileCorrectly() throws Exception {
        Path path = getExcelPath("test_data.xlsx");

        ExcelParserConfig config = ExcelParserConfig.builder()
                .hasHeader(true)
                .sheetIndex(0)
                .build();

        ExcelParser parser = new ExcelParser(path, config);
        DataFrame df = parser.parse();

        assertAll(
                () -> assertNotNull(df),
                () -> assertEquals(4, df.columnCount()),
                () -> assertLinesMatch(List.of("id", "name", "age", "active"), df.getColumnNames())
        );
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {
        Path path = Paths.get("not_exist_file.xlsx");

        ExcelParserConfig config = ExcelParserConfig.builder()
                .hasHeader(true)
                .sheetIndex(0)
                .build();

        ExcelParser parser = new ExcelParser(path, config);

        var exception = assertThrows(ExcelReadException.class, parser::parse);

        assertTrue(exception.getMessage().contains("Failed to read Excel file"));
    }

    @Test
    void shouldThrowWhenSheetNameNotFound() throws Exception {
        Path path = getExcelPath("test_data.xlsx");

        ExcelParserConfig config = ExcelParserConfig.builder()
                .hasHeader(true)
                .sheetName("NOT_EXIST")
                .build();

        ExcelParser parser = new ExcelParser(path, config);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }

    @Test
    void shouldThrowWhenSheetIndexNotFound() throws Exception {
        Path path = getExcelPath("test_data.xlsx");

        ExcelParserConfig config = ExcelParserConfig.builder()
                .hasHeader(true)
                .sheetIndex(999)
                .build();

        ExcelParser parser = new ExcelParser(path, config);

        assertThrows(IllegalArgumentException.class, parser::parse);
    }
}
