package com.jchojdak.jproxima.impl.io.csv;

import com.jchojdak.jproxima.data.*;
import com.jchojdak.jproxima.impl.io.type.TypeInferrer;
import com.jchojdak.jproxima.impl.io.type.ValueConverter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses CSV files into DataFrames.
 */
final class CsvParser {

    private final Path path;
    private final CsvParserConfig config;
    private final TypeInferrer typeInferrer;
    private final ValueConverter valueConverter;

    CsvParser(Path path, CsvParserConfig config) {
        this.path = path;
        this.config = config;
        this.typeInferrer = new TypeInferrer(config.nullValue());
        this.valueConverter = new ValueConverter(config.nullValue());
    }

    DataFrame parse() {
        try {
            CSVFormat format = buildCsvFormat();

            if (config.skipRows() > 0) {
                return parseWithSkipRows(format);
            } else {
                return parseDirectly(format);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + path, e);
        }
    }

    private CSVFormat buildCsvFormat() {
        CSVFormat.Builder builder = CSVFormat.DEFAULT.builder()
                .setDelimiter(config.delimiter())
                .setQuote(config.quote())
                .setEscape(config.escape())
                .setNullString(config.nullValue());

        if (config.hasHeader()) {
            builder.setHeader()
                    .setSkipHeaderRecord(true);
        }

        return builder.get();
    }

    private DataFrame parseWithSkipRows(CSVFormat format) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, config.encoding())) {
            for (int i = 0; i < config.skipRows(); i++) {
                reader.readLine();
            }

            try (CSVParser csvParser = CSVParser.parse(reader, format)) {
                return buildDataFrame(csvParser);
            }
        }
    }

    private DataFrame parseDirectly(CSVFormat format) throws IOException {
        try (CSVParser csvParser = CSVParser.parse(path, config.encoding(), format)) {
            return buildDataFrame(csvParser);
        }
    }

    private DataFrame buildDataFrame(CSVParser csvParser) {
        List<CSVRecord> records = csvParser.getRecords();

        if (records.isEmpty()) {
            return DataFrameBuilder.create().build();
        }

        String[] columnNames = getColumnNames(csvParser, records.getFirst());
        List<List<String>> columnData = extractColumnData(records, columnNames.length);

        return buildDataFrameFromColumns(columnNames, columnData);
    }

    private String[] getColumnNames(CSVParser csvParser, CSVRecord firstRecord) {
        if (config.hasHeader() && csvParser.getHeaderMap() != null && !csvParser.getHeaderMap().isEmpty()) {
            return extractHeaderNames(csvParser);
        } else {
            return generateDefaultColumnNames(firstRecord.size());
        }
    }

    private String[] extractHeaderNames(CSVParser csvParser) {
        String[] headers = new String[csvParser.getHeaderMap().size()];

        csvParser.getHeaderMap()
                .forEach((name, index) -> headers[index] = name);

        return headers;
    }

    private String[] generateDefaultColumnNames(int count) {
        String[] headers = new String[count];
        for (int i = 0; i < count; i++) {
            headers[i] = "Column" + i;
        }
        return headers;
    }

    private List<List<String>> extractColumnData(List<CSVRecord> records, int columnCount) {
        List<List<String>> columnData = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            columnData.add(new ArrayList<>());
        }

        for (CSVRecord record : records) {
            for (int i = 0; i < columnCount && i < record.size(); i++) {
                columnData.get(i).add(record.get(i));
            }
        }

        return columnData;
    }

    private DataFrame buildDataFrameFromColumns(String[] columnNames, List<List<String>> columnData) {
        DataFrameBuilder dataFrameBuilder = DataFrameBuilder.create();

        for (int i = 0; i < columnNames.length; i++) {
            Column column = buildColumn(columnNames[i], columnData.get(i));
            dataFrameBuilder.addColumn(column);
        }

        return dataFrameBuilder.build();
    }

    private Column buildColumn(String name, List<String> data) {
        DataType type = typeInferrer.inferType(data);
        Object[] typedData = valueConverter.convertAll(data, type);

        return ColumnBuilder.create()
                .name(name)
                .type(type)
                .addAll(typedData)
                .build();
    }
}