package com.jchojdak.jproxima.impl.io.csv;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    private Path getCsvPath(String fileName) throws URISyntaxException {
        return Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("csv/" + fileName)).toURI()
        );
    }

    @ParameterizedTest
    @CsvSource({
            "with_header.csv,true",
            "without_header.csv,false",
            "skip_rows.csv,true"
    })
    void shouldParseCsvFilesCorrectly(String fileName, boolean hasHeader) throws Exception {
        Path path = getCsvPath(fileName);

        CsvParserConfig.Builder configBuilder = CsvParserConfig.builder()
                .hasHeader(hasHeader);

        if ("skip_rows.csv".equals(fileName)) {
            configBuilder.skipRows(2);
        }

        CsvParser parser = new CsvParser(path, configBuilder.build());
        DataFrame df = parser.parse();

        assertNotNull(df);
        assertEquals(4, df.columnCount());

        if (hasHeader) {
            assertEquals(DataType.INTEGER, df.getColumn("id").getType());
            assertEquals(DataType.STRING, df.getColumn("name").getType());
            assertEquals(DataType.INTEGER, df.getColumn("age").getType());
            assertEquals(DataType.BOOLEAN, df.getColumn("active").getType());
        }
    }
}
