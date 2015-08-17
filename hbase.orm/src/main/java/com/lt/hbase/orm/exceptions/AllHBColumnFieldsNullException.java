package com.lt.hbase.orm.exceptions;

public class AllHBColumnFieldsNullException extends IllegalArgumentException {
    public AllHBColumnFieldsNullException(String s) {
        super(s);
    }

    public AllHBColumnFieldsNullException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
