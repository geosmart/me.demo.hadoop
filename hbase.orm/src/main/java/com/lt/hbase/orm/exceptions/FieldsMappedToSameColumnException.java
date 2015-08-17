package com.lt.hbase.orm.exceptions;

public class FieldsMappedToSameColumnException extends IllegalArgumentException {
    public FieldsMappedToSameColumnException(String s) {
        super(s);
    }

    public FieldsMappedToSameColumnException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
