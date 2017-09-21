package ys.prototype.fmtaq.application.dto;

import lombok.Data;
import ys.prototype.fmtaq.domain.CommandResponseStatus;

import java.util.UUID;

@Data
public class CommandResponseDTO {

    private final UUID CommandId;
    private final CommandResponseStatus responseStatus;
    private final String body;
}
