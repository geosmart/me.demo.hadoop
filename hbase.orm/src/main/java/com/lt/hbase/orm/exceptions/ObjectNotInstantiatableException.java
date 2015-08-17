package com.lt.hbase.orm.exceptions;

public class ObjectNotInstantiatableException extends IllegalArgumentException {
    public ObjectNotInstantiatableException(String s) {
        super(s);
    }

    public ObjectNotInstantiatableException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
