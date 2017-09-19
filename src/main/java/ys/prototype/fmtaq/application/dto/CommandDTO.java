package ys.prototype.fmtaq.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommandDTO {

    @NotNull(message = "Command address cannot be null")
    private final String address;

    @NotNull(message = "Command body cannot be null")
    private final String body;
}
