package com.jchojdak.jproxima.data;

public interface DataFrame {

    int rowCount();

    int columnCount();

    Column getColumn(String name);

    DataFrame getColumns(String... names);

    DataFrame addColumn(String name, Column column);

    DataFrame dropColumn(String name);

    DataFrame head(int n);

    DataFrame tail(int n);

    Object[][] toArray();
}
