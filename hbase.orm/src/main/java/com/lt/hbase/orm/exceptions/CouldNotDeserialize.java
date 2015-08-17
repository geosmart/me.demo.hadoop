package com.lt.hbase.orm.exceptions;

public class CouldNotDeserialize extends IllegalStateException {
    public CouldNotDeserialize(Throwable e) {
        super(e);
    }
}
