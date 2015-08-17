package com.lt.hbase.orm.exceptions;

public class RowKeyCantBeComposedException extends IllegalArgumentException {
    public RowKeyCantBeComposedException(String s) {
        super(s);
    }

    public RowKeyCantBeComposedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
