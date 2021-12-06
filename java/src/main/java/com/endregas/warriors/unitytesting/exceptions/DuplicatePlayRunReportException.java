package com.endregas.warriors.unitytesting.exceptions;

public class DuplicatePlayRunReportException extends Exception {

    public DuplicatePlayRunReportException() {
        super("A report was already uploaded for this play run");
    }

}
