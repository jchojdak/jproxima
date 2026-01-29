package com.jchojdak.jproxima.io.read;

import com.jchojdak.jproxima.data.DataFrame;

import java.nio.charset.Charset;

/**
 * Builder for creating CSV readers that parse CSV files into {@link DataFrame} instances.
 * <p>
 * This builder follows the fluent API pattern and allows configuring various
 * CSV parsing options such as delimiter, header presence, encoding, and special character handling.
 * <p>
 * The {@link #build()} method triggers parsing and returns the resulting {@link DataFrame}.
 *
 * @see DataFrame
 */
public interface CsvReaderBuilder {

    /**
     * Sets the column delimiter character.
     *
     * @param delimiter the delimiter character (e.g. ',', ';')
     * @return {@code this} builder instance
     */
    CsvReaderBuilder delimiter(char delimiter);

    /**
     * Sets the column delimiter using a string.
     * <p>
     * Only the first character of the string is used.
     *
     * @param delimiter the delimiter string
     * @return {@code this} builder instance
     */
    CsvReaderBuilder delimiter(String delimiter);

    /**
     * Specifies whether the CSV file contains a header row.
     *
     * @param hasHeader {@code true} if the first row contains column names,
     *                  {@code false} otherwise
     * @return {@code this} builder instance
     */
    CsvReaderBuilder header(boolean hasHeader);

    /**
     * Sets the quote character used to wrap field values.
     *
     * @param quote quote the quote character (e.g. '"')
     * @return {@code this} builder instance
     */
    CsvReaderBuilder quote(char quote);

    /**
     * Sets the escape character used inside quoted values.
     *
     * @param escape the escape character
     * @return {@code this} builder instance
     */
    CsvReaderBuilder escape(char escape);

    /**
     * Sets the character encoding used to read the CSV file.
     *
     * @param charset the character set
     * @return {@code this} builder instance
     */
    CsvReaderBuilder encoding(Charset charset);

    /**
     * Sets the character encoding used to read the CSV file.
     *
     * @param charsetName the name of the character set (e.g. "UTF-8")
     * @return {@code this} builder instance
     */
    CsvReaderBuilder encoding(String charsetName);

    /**
     * Skips the given number of initial rows before parsing data.
     *
     * @param rows number of rows to skip
     * @return {@code this} builder instance
     */
    CsvReaderBuilder skipRows(int rows);

    /**
     * Defines a string value that should be interpreted as {@code null}.
     *
     * @param nullValue the string representing a null value
     * @return {@code this} builder instance
     */
    CsvReaderBuilder nullValue(String nullValue);

    /**
     * Builds the CSV reader and parses the file into the {@link DataFrame}.
     *
     * @return parsed {@link DataFrame}
     */
    DataFrame build();
}
