package ys.prototype.fmtaq.command.application.dto;

import lombok.Data;
import ys.prototype.fmtaq.command.domain.CommandResponseStatus;

import java.util.UUID;

@Data
public class CommandResponseDTO {

    private final UUID CommandId;
    private final CommandResponseStatus responseStatus;
    private final String body;
}
