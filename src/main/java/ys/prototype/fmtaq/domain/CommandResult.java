package ys.prototype.fmtaq.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class CommandResult {
    private UUID commandId;
    private final CommandStatus status;
    private final String body;

    public Boolean isStatusOk() {
        return status == CommandStatus.OK;
    }
}
