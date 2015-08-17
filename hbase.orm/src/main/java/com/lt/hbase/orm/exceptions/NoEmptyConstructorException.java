package com.lt.hbase.orm.exceptions;

public class NoEmptyConstructorException extends IllegalArgumentException {
    public NoEmptyConstructorException(String s) {
        super(s);
    }

    public NoEmptyConstructorException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
