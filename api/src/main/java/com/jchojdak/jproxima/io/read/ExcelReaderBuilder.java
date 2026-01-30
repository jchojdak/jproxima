package com.jchojdak.jproxima.io.read;

import com.jchojdak.jproxima.data.DataFrame;

/**
 * Builder for creating Excel readers that parse Excel worksheets into {@link DataFrame} instances.
 * <p>
 * This builder follows the fluent API pattern and allows configuring various
 * CSV parsing options such as sheet and header presence.
 * <p>
 * The {@link #build()} method triggers parsing and returns the resulting {@link DataFrame}.
 *
 * @see DataFrame
 */
public interface ExcelReaderBuilder {

    /**
     * Selects a worksheet by name.
     *
     * @param sheetName the name of the worksheet
     * @return {@code this} builder instance
     */
    ExcelReaderBuilder sheet(String sheetName);

    /**
     * Selects a worksheet by index.
     *
     * @param sheetIndex the index of the worksheet (starting from 0)
     * @return {@code this} builder instance
     */
    ExcelReaderBuilder sheet(int sheetIndex);

    /**
     * Specifies whether the selected worksheet contains a header row.
     *
     * @param hasHeader {@code true} if the first row contains column names,
     *                  {@code false} otherwise
     * @return {@code this} builder instance
     */
    ExcelReaderBuilder header(boolean hasHeader);

    /**
     * Builds the Excel reader and parses the selected worksheet into the {@link DataFrame}.
     *
     * @return parsed {@link DataFrame}
     */
    DataFrame build();
}
