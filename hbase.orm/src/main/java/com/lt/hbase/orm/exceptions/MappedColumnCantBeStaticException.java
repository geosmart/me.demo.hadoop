package com.lt.hbase.orm.exceptions;

public class MappedColumnCantBeStaticException extends IllegalArgumentException {
    public MappedColumnCantBeStaticException(String s) {
        super(s);
    }

    public MappedColumnCantBeStaticException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
