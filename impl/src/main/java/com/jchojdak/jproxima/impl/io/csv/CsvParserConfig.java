package com.jchojdak.jproxima.impl.io.csv;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

record CsvParserConfig(
        char delimiter,
        boolean hasHeader,
        char quote,
        char escape,
        Charset encoding,
        int skipRows,
        String nullValue
) {

    private static final char DEFAULT_DELIMITER = ',';
    private static final boolean DEFAULT_HAS_HEADER = true;
    private static final char DEFAULT_QUOTE = '"';
    private static final char DEFAULT_ESCAPE = '\\';
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
    private static final int DEFAULT_SKIP_ROWS = 0;
    private static final String DEFAULT_NULL_VALUE = "";

    CsvParserConfig {
        if (skipRows < 0) {
            throw new IllegalArgumentException("skipRows cannot be negative");
        }
        if (nullValue == null) {
            nullValue = DEFAULT_NULL_VALUE;
        }
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private char delimiter = DEFAULT_DELIMITER;
        private boolean hasHeader = DEFAULT_HAS_HEADER;
        private char quote = DEFAULT_QUOTE;
        private char escape = DEFAULT_ESCAPE;
        private Charset encoding = DEFAULT_ENCODING;
        private int skipRows = DEFAULT_SKIP_ROWS;
        private String nullValue = DEFAULT_NULL_VALUE;

        Builder delimiter(char delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        Builder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        Builder quote(char quote) {
            this.quote = quote;
            return this;
        }

        Builder escape(char escape) {
            this.escape = escape;
            return this;
        }

        Builder encoding(Charset encoding) {
            this.encoding = encoding;
            return this;
        }

        Builder skipRows(int skipRows) {
            this.skipRows = skipRows;
            return this;
        }

        Builder nullValue(String nullValue) {
            this.nullValue = nullValue;
            return this;
        }

        CsvParserConfig build() {
            return new CsvParserConfig(
                    delimiter,
                    hasHeader,
                    quote,
                    escape,
                    encoding,
                    skipRows,
                    nullValue
            );
        }
    }
}