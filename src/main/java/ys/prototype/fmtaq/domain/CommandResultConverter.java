package ys.prototype.fmtaq.domain;

import java.util.UUID;

public interface CommandResultConverter {
    UUID getCommandId();

    Boolean isStatusOk();
}
