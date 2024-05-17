package com.allens.onlinelog.apis;

import lombok.Getter;
import lombok.Setter;

/**
 * OffsetResult
 *
 * @author allens
 * @since 2024/5/8
 */
@Setter
@Getter
public class OffsetResult {

    private Long offset;

    private String logs;

    public OffsetResult(Long offset, String logs) {
        this.offset = offset;
        this.logs = logs;
    }

    public OffsetResult() {
    }

    public static OffsetResult empty () {
        OffsetResult offsetResult = new OffsetResult();
        offsetResult.setOffset(0L);
        offsetResult.setLogs("");
        return offsetResult;
    }

    public static OffsetResult empty (long offset) {
        OffsetResult offsetResult = new OffsetResult();
        offsetResult.setOffset(offset);
        offsetResult.setLogs("");
        return offsetResult;
    }

}
