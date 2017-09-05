package ys.prototype.fmtaq.command.domain;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.task.group.CommandGroup;
import ys.prototype.fmtaq.command.domain.task.sequence.CommandSequence;
import ys.prototype.fmtaq.command.domain.task.group.TaskGroup;
import ys.prototype.fmtaq.command.domain.task.sequence.TaskSequence;

import java.util.UUID;

@Component
public class ModelFactory {

    public TaskSequence createSequence(UUID id) {
        return new TaskSequence(id);
    }

    public TaskGroup createGroup(UUID id) {
        return new TaskGroup(id);
    }

    public CommandSequence createCommandSequence(UUID id, UUID nextCommandId, String address, String body) {
        return new CommandSequence(id, nextCommandId, address, body);
    }

    public CommandGroup createCommandGroup(UUID id, String address, String body) {
        return new CommandGroup(id, address, body);
    }
}
