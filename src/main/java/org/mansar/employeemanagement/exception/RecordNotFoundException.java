package org.mansar.employeemanagement.exception;

public class RecordNotFoundException extends RuntimeException {
    private final Long recordId;
    private final String recordName;
    private final String keyword;
    public RecordNotFoundException(Long recordId, String recordName) {
        super("Record not found");
        this.recordId = recordId;
        this.recordName = recordName;
        this.keyword = null;
    }

    public RecordNotFoundException(String key, String recordName) {
        super("Record not found");
        this.recordId = null;
        this.recordName = recordName;
        this.keyword = key;
    }
}
