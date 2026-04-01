package com.jchojdak.jproxima.impl.io.excel;

import com.jchojdak.jproxima.data.DataFrame;
import com.jchojdak.jproxima.io.read.ExcelReaderBuilder;

import java.nio.file.Path;

/**
 * Default implementation of {@link ExcelReaderBuilder}.
 *
 * @see ExcelReaderBuilder
 */
public final class DefaultExcelReaderBuilder implements ExcelReaderBuilder {

    private final Path path;
    private final ExcelParserConfig.Builder configBuilder;

    public DefaultExcelReaderBuilder(Path path) {
        this.path = path;
        this.configBuilder = ExcelParserConfig.builder();
    }

    @Override
    public ExcelReaderBuilder sheet(String sheetName) {
        configBuilder.sheetName(sheetName);
        return this;
    }

    @Override
    public ExcelReaderBuilder sheet(int sheetIndex) {
        configBuilder.sheetIndex(sheetIndex);
        return this;
    }

    @Override
    public ExcelReaderBuilder header(boolean hasHeader) {
        configBuilder.hasHeader(hasHeader);
        return this;
    }

    @Override
    public DataFrame build() {
        ExcelParserConfig config = configBuilder.build();
        ExcelParser parser = new ExcelParser(path, config);
        return parser.parse();
    }
}

