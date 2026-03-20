package com.jchojdak.jproxima.impl.io.csv;

import com.jchojdak.jproxima.data.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Validates and writes data to CSV format.
 */
public class CsvWriter {

    private CsvWriter() {
    }

    public static void write(String path, Collection<Column> columns) {
        write(Path.of(path), columns);
    }

    public static void write(Path path, Collection<Column> columns) {
        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            List<Column> columnList = new ArrayList<>(columns);

            if (columnList.isEmpty()) {
                return;
            }

            List<String> headers = new ArrayList<>();
            for (Column col : columnList) {
                headers.add(col.getName());
            }
            csvPrinter.printRecord(headers);

            int rowCount = columnList.getFirst().size();
            int colCount = columnList.size();

            List<ColumnWriter> writers = new ArrayList<>(colCount);
            for (Column col : columnList) {
                writers.add(createWriter(col));
            }

            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    writers.get(j).write(i, csvPrinter);
                }
                csvPrinter.println();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file: " + path, e);
        }
    }

    private static ColumnWriter createWriter(Column column) {
        return (index, printer) ->
                printer.print(column.isNull(index) ? "" : column.get(index));
    }

    @FunctionalInterface
    private interface ColumnWriter {
        void write(int index, CSVPrinter printer) throws IOException;
    }
}
