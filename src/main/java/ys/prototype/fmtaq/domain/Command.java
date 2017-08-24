package ys.prototype.fmtaq.domain;

import java.util.UUID;

public interface Command {
    String getAddress();

    String getBody();

    UUID updateTaskStatusAndGetNextCommandId(CommandResponseStatus commandResponseStatus);
}
