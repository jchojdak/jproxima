package com.jchojdak.jproxima.impl.io.excel;

/**
 * Configuration for Excel parsing.
 * <p>
 * This record holds all parameters that control how an Excel file is parsed
 *
 * @param sheetIndex the index of the sheet to parse (0-based)
 * @param sheetName  the name of the sheet to parse (overrides sheetIndex if provided)
 * @param hasHeader  whether the first row is a header
 */
record ExcelParserConfig(
        int sheetIndex,
        String sheetName,
        boolean hasHeader
) {

    private static final int DEFAULT_SHEET_INDEX = 0;
    private static final String DEFAULT_SHEET_NAME = null;
    private static final boolean DEFAULT_HAS_HEADER = true;

    static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private int sheetIndex = DEFAULT_SHEET_INDEX;
        private String sheetName = DEFAULT_SHEET_NAME;
        private boolean hasHeader = DEFAULT_HAS_HEADER;

        Builder sheetIndex(int sheetIndex) {
            this.sheetIndex = sheetIndex;
            return this;
        }

        Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        Builder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        ExcelParserConfig build() {
            return new ExcelParserConfig(
                    sheetIndex,
                    sheetName,
                    hasHeader
            );
        }
    }
}
