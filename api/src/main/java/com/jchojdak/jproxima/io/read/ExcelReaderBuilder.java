package com.jchojdak.jproxima.io.read;

import com.jchojdak.jproxima.data.DataFrame;

public interface ExcelReaderBuilder {

    ExcelReaderBuilder sheet(String sheetName);

    ExcelReaderBuilder sheet(int sheetIndex);

    ExcelReaderBuilder header(boolean hasHeader);

    DataFrame build();
}
