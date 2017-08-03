package ys.prototype.fmtaq.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Task {
    private UUID id;
    private String command;
    private String status;
    private Task nextTask;
}
