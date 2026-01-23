package com.jchojdak.jproxima.data;

public interface Column {

    Object get(int index);

    int size();

    DataType getType();

    Object[] toArray();

    String getName();
}
