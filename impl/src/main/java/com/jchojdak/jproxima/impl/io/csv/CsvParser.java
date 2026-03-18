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
import java.util.*;

/**
 * Parses CSV files into DataFrames.
 */
final class CsvParser {

    private static final int BLOCK_SIZE = 10_000;

    private final Path path;
    private final CsvParserConfig config;
    private final TypeInferrer typeInferrer;
    private final ValueConverter converter;

    CsvParser(Path path, CsvParserConfig config) {
        this.path = path;
        this.config = config;
        this.typeInferrer = new TypeInferrer(config.nullValue());
        this.converter = new ValueConverter(config.nullValue());
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

    private DataFrame parseDirectly(CSVFormat format) throws IOException {
        try (CSVParser parser = CSVParser.parse(path, config.encoding(), format)) {
            return buildDataFrame(parser);
        }
    }

    private DataFrame parseWithSkipRows(CSVFormat format) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, config.encoding())) {

            for (int i = 0; i < config.skipRows(); i++) {
                reader.readLine();
            }

            try (CSVParser parser = CSVParser.parse(reader, format)) {
                return buildDataFrame(parser);
            }
        }
    }

    private DataFrame buildDataFrame(CSVParser parser) {
        Iterator<CSVRecord> it = parser.iterator();

        if (!it.hasNext()) {
            return DataFrameBuilder.create().build();
        }

        CSVRecord firstRow = it.next();

        String[] columnNames = getColumnNames(parser, firstRow);
        int columnCount = columnNames.length;

        DataType[] columnTypes = inferColumnTypes(firstRow, columnCount);
        List<List<Object>> buffers = createBuffers(columnCount);

        DataFrameBuilder dfBuilder = DataFrameBuilder.create();

        int rowCount = 0;

        addRow(firstRow, columnCount, buffers, columnTypes);
        rowCount++;

        while (it.hasNext()) {
            CSVRecord row = it.next();
            addRow(row, columnCount, buffers, columnTypes);
            rowCount++;

            if (rowCount >= BLOCK_SIZE) {
                flushBuffers(dfBuilder, columnNames, columnTypes, buffers);
                rowCount = 0;
            }
        }

        flushBuffers(dfBuilder, columnNames, columnTypes, buffers);

        return dfBuilder.build();
    }

    private DataType[] inferColumnTypes(CSVRecord firstRow, int columnCount) {
        DataType[] types = new DataType[columnCount];

        for (int i = 0; i < columnCount; i++) {
            types[i] = typeInferrer.inferType(firstRow.get(i));
        }

        return types;
    }

    private List<List<Object>> createBuffers(int columnCount) {
        List<List<Object>> buffers = new ArrayList<>(columnCount);

        for (int i = 0; i < columnCount; i++) {
            buffers.add(new ArrayList<>(BLOCK_SIZE));
        }

        return buffers;
    }

    private void addRow(CSVRecord row,
                           int columnCount,
                           List<List<Object>> buffers,
                           DataType[] types) {

        for (int i = 0; i < columnCount && i < row.size(); i++) {
            Object value = converter.convert(row.get(i), types[i]);
            buffers.get(i).add(value);
        }
    }

    private void flushBuffers(DataFrameBuilder dfBuilder,
                              String[] columnNames,
                              DataType[] types,
                              List<List<Object>> buffers) {

        for (int i = 0; i < columnNames.length; i++) {
            List<Object> buf = buffers.get(i);

            if (!buf.isEmpty()) {
                dfBuilder.addColumn(columnNames[i], buf.toArray(), types[i]);
                buf.clear();
            }
        }
    }

    private CSVFormat buildCsvFormat() {
        CSVFormat.Builder builder = CSVFormat.DEFAULT.builder()
                .setDelimiter(config.delimiter())
                .setQuote(config.quote())
                .setEscape(config.escape())
                .setNullString(config.nullValue());

        if (config.hasHeader()) {
            builder.setHeader().setSkipHeaderRecord(true);
        }

        return builder.get();
    }

    private String[] getColumnNames(CSVParser parser, CSVRecord firstRow) {
        if (config.hasHeader()
                && parser.getHeaderMap() != null
                && !parser.getHeaderMap().isEmpty()) {

            return extractHeaderNames(parser);
        }

        return generateDefaultColumnNames(firstRow.size());
    }

    private String[] extractHeaderNames(CSVParser parser) {
        String[] headers = new String[parser.getHeaderMap().size()];

        parser.getHeaderMap()
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
}