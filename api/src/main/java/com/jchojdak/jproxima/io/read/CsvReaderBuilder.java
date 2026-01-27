package com.jchojdak.jproxima.io.read;

import com.jchojdak.jproxima.data.DataFrame;

import java.nio.charset.Charset;

public interface CsvReaderBuilder {

    CsvReaderBuilder delimiter(char delimiter);

    CsvReaderBuilder delimiter(String delimiter);

    CsvReaderBuilder header(boolean hasHeader);

    CsvReaderBuilder quote(char quote);

    CsvReaderBuilder escape(char escape);

    CsvReaderBuilder encoding(Charset charset);

    CsvReaderBuilder encoding(String charsetName);

    CsvReaderBuilder skipRows(int rows);

    CsvReaderBuilder nullValue(String nullValue);

    DataFrame build();
}
