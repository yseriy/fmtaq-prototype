package ys.prototype.fmtaq.domain;

import java.util.UUID;

public interface TaskMetadata {
    public UUID updateTaskStateAndGetNextCommandId(ResponseStatus responseStatus);
}
