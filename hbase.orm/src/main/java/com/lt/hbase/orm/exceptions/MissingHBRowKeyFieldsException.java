package com.lt.hbase.orm.exceptions;

public class MissingHBRowKeyFieldsException extends IllegalArgumentException {
    public MissingHBRowKeyFieldsException(String s) {
        super(s);
    }

    public MissingHBRowKeyFieldsException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
