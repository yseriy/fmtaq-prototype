package ys.prototype.fmtaq.domain;

import lombok.Data;

import java.util.List;

@Data
public class TaskData {
    private final TaskType type;
    private final List<CommandData> commands;
}
