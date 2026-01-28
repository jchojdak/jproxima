package com.jchojdak.jproxima.impl.io.csv;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.io.read.CsvReaderBuilder;

import java.nio.charset.Charset;
import java.nio.file.Path;

public final class DefaultCsvReaderBuilder implements CsvReaderBuilder {

    private final Path path;
    private final CsvParserConfig.Builder configBuilder;

    public DefaultCsvReaderBuilder(Path path) {
        this.path = path;
        this.configBuilder = CsvParserConfig.builder();
    }

    @Override
    public CsvReaderBuilder delimiter(char delimiter) {
        configBuilder.delimiter(delimiter);
        return this;
    }

    @Override
    public CsvReaderBuilder delimiter(String delimiter) {
        return delimiter(delimiter.charAt(0));
    }

    @Override
    public CsvReaderBuilder header(boolean hasHeader) {
        configBuilder.hasHeader(hasHeader);
        return this;
    }

    @Override
    public CsvReaderBuilder quote(char quote) {
        configBuilder.quote(quote);
        return this;
    }

    @Override
    public CsvReaderBuilder escape(char escape) {
        configBuilder.escape(escape);
        return this;
    }

    @Override
    public CsvReaderBuilder encoding(Charset charset) {
        configBuilder.encoding(charset);
        return this;
    }

    @Override
    public CsvReaderBuilder encoding(String charsetName) {
        return encoding(Charset.forName(charsetName));
    }

    @Override
    public CsvReaderBuilder skipRows(int rows) {
        configBuilder.skipRows(rows);
        return this;
    }

    @Override
    public CsvReaderBuilder nullValue(String nullValue) {
        configBuilder.nullValue(nullValue);
        return this;
    }

    @Override
    public DataFrame build() {
        CsvParserConfig config = configBuilder.build();
        CsvParser parser = new CsvParser(path, config);
        return parser.parse();
    }
}
