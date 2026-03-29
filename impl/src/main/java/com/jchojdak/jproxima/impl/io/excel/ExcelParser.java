package com.jchojdak.jproxima.impl.io.excel;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.data.DataFrameBuilder;
import com.jchojdak.jproxima.data.DataType;
import com.jchojdak.jproxima.impl.io.type.TypeInferrer;
import com.jchojdak.jproxima.impl.io.type.ValueConverter;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Parses Excel files into DataFrames.
 */
final class ExcelParser {

    private static final int INITIAL_BUFFER_CAPACITY = 10_000;

    private final Path path;
    private final ExcelParserConfig config;
    private final TypeInferrer inferrer;
    private final ValueConverter converter;

    ExcelParser(Path path, ExcelParserConfig config) {
        this.path = path;
        this.config = config;
        this.inferrer = new TypeInferrer(null);
        this.converter = new ValueConverter(null);
    }

    DataFrame parse() {
        try (InputStream is = Files.newInputStream(path);
             ReadableWorkbook wb = new ReadableWorkbook(is)) {

            Sheet sheet;
            if (config.sheetName() != null) {
                sheet = wb.findSheet(config.sheetName())
                        .orElseThrow(() -> new IllegalArgumentException("Sheet not found: " + config.sheetName()));
            } else {
                sheet = wb.getSheet(config.sheetIndex())
                        .orElseThrow(() -> new IllegalArgumentException("Sheet not found at index: " + config.sheetIndex()));
            }

            try (Stream<Row> rowsStream = sheet.openStream()) {
                return buildDataFrame(rowsStream.iterator());
            }

        } catch (IOException e) {
            throw new ExcelReadException("Failed to read Excel file: " + path, e);
        }
    }

    private DataFrame buildDataFrame(Iterator<Row> rows) {
        if (!rows.hasNext()) {
            return DataFrameBuilder.create().build();
        }

        Row firstRow = rows.next();
        int columnCount = firstRow.getCellCount();

        String[] columnNames = getColumnNames(firstRow, columnCount);

        if (config.hasHeader()) {
            if (!rows.hasNext()) {
                return DataFrameBuilder.create().build();
            }
            firstRow = rows.next();
        }

        DataType[] columnTypes = inferColumnTypes(firstRow, columnCount);
        List<List<Object>> buffers = createBuffers(columnCount);

        DataFrameBuilder builder = DataFrameBuilder.create();

        addRow(firstRow, columnCount, buffers, columnTypes);

        while (rows.hasNext()) {
            Row row = rows.next();
            addRow(row, columnCount, buffers, columnTypes);
        }

        flushBuffers(builder, columnNames, columnTypes, buffers);

        return builder.build();
    }

    private DataType[] inferColumnTypes(Row firstRow, int columnCount) {
        DataType[] types = new DataType[columnCount];
        for (int i = 0; i < columnCount; i++) {
            types[i] = inferrer.inferType(getCellValueAsString(firstRow, i));
        }
        return types;
    }

    private List<List<Object>> createBuffers(int columnCount) {
        List<List<Object>> buffers = new ArrayList<>(columnCount);
        for (int i = 0; i < columnCount; i++) {
            buffers.add(new ArrayList<>(INITIAL_BUFFER_CAPACITY));
        }
        return buffers;
    }

    private void addRow(Row row, int columnCount, List<List<Object>> buffers, DataType[] types) {
        for (int i = 0; i < columnCount; i++) {
            String cellString = getCellValueAsString(row, i);
            Object value = converter.convert(cellString, types[i]);
            buffers.get(i).add(value);
        }
    }

    private String getCellValueAsString(Row row, int colIndex) {
        if (colIndex >= row.getCellCount()) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        return cell == null ? null : cell.getText();
    }

    private void flushBuffers(DataFrameBuilder builder, String[] columnNames, DataType[] types, List<List<Object>> buffers) {
        for (int i = 0; i < columnNames.length; i++) {
            List<Object> columnBuffer = buffers.get(i);
            if (!columnBuffer.isEmpty()) {
                builder.addColumn(columnNames[i], columnBuffer.toArray(), types[i]);
                columnBuffer.clear();
            }
        }
    }

    private String[] getColumnNames(Row firstRow, int columnCount) {
        if (config.hasHeader()) {
            String[] headers = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                headers[i] = getCellValueAsString(firstRow, i);
            }
            return headers;
        }
        return generateDefaultColumnNames(columnCount);
    }

    private String[] generateDefaultColumnNames(int count) {
        String[] headers = new String[count];
        for (int i = 0; i < count; i++) {
            headers[i] = "Column" + i;
        }
        return headers;
    }
}
