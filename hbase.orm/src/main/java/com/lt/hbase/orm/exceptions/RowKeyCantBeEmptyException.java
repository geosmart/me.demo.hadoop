package com.lt.hbase.orm.exceptions;

public class RowKeyCantBeEmptyException extends IllegalArgumentException {
    public RowKeyCantBeEmptyException(String s) {
        super(s);
    }

    public RowKeyCantBeEmptyException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
