package com.lt.hbase.orm.exceptions;

public class MissingHBColumnFieldsException extends IllegalArgumentException {
    public MissingHBColumnFieldsException(String s) {
        super(s);
    }

    public MissingHBColumnFieldsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
