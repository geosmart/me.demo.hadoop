package com.lt.hbase.orm.exceptions;

public class MappedColumnCantBePrimitiveException extends IllegalArgumentException {
    public MappedColumnCantBePrimitiveException(String s) {
        super(s);
    }

    public MappedColumnCantBePrimitiveException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
