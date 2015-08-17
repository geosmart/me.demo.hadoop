package com.lt.hbase.orm.exceptions;

/**
 * Thrown when {@link com.lt.hbase.orm.HBObjectMapper HBObjectMapper} fails to convert bean-like objects to HBase data types or vice-versa
 */
public class BadHBaseLibStateException extends IllegalStateException {
    private static final String BAD_HBASE_STATE_ERROR = "Unknown error - possibly, HBase library is unavailable at runtime or an incorrect version is being used";

    public BadHBaseLibStateException(Throwable throwable) {
        super(BAD_HBASE_STATE_ERROR, throwable);
    }
}
