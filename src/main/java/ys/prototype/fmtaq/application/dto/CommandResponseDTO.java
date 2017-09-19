package ys.prototype.fmtaq.application.dto;

import lombok.Data;
import ys.prototype.fmtaq.domain.CommandResponseStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CommandResponseDTO {

    @NotNull
    private final UUID CommandId;

    @NotNull
    private final CommandResponseStatus responseStatus;

    @NotNull
    private final String body;
}
