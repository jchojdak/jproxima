package com.jchojdak.jproxima.impl.io.excel;

import com.jchojdak.jproxima.data.Column;
import com.jchojdak.jproxima.data.DataFrame;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes DataFrame to Excel (.xlsx) files.
 */
public class ExcelWriter {

    private ExcelWriter() {
    }

    /**
     * Writes the given DataFrame to an Excel file at the specified path.
     *
     * @param path      target file path
     * @param dataFrame DataFrame to write
     */
    public static void write(String path, DataFrame dataFrame) {
        write(Path.of(path), dataFrame);
    }

    /**
     * Writes the given DataFrame to an Excel file at the specified path.
     *
     * @param path      target file path
     * @param dataFrame DataFrame to write
     */
    public static void write(Path path, DataFrame dataFrame) {
        List<String> columnNames = dataFrame.getColumnNames();

        if (columnNames.isEmpty()) {
            return;
        }

        List<Column> columns = columnNames.stream()
                .map(dataFrame::getColumn)
                .toList();

        int rowCount = dataFrame.rowCount();

        try (OutputStream os = Files.newOutputStream(path);
             Workbook wb = new Workbook(os, "jproxima", "1.0")) {

            Worksheet sheet = wb.newWorksheet("sheet1");

            writeHeaders(sheet, columnNames);
            writeRows(sheet, columns, rowCount);

        } catch (IOException e) {
            throw new ExcelWriteException("Error writing Excel file: " + path);
        }
    }

    private static void writeHeaders(Worksheet sheet, List<String> columnNames) {
        for (int c = 0; c < columnNames.size(); c++) {
            sheet.value(0, c, columnNames.get(c));
        }
    }

    private static void writeRows(Worksheet sheet, List<Column> columns, int rowCount) {
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columns.size(); c++) {
                writeValue(sheet, r + 1, c, columns.get(c).get(r));
            }
        }
    }

    private static void writeValue(Worksheet sheet, int r, int c, Object value) {
        if (value == null) {
            return;
        }

        switch (value) {
            case Number n -> sheet.value(r, c, n.doubleValue());
            case Boolean b -> sheet.value(r, c, b);
            default -> sheet.value(r, c, value.toString());
        }
    }
}