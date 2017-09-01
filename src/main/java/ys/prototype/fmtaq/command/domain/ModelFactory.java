package ys.prototype.fmtaq.command.domain;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.task.GroupedCommand;
import ys.prototype.fmtaq.command.domain.task.LinkedCommand;
import ys.prototype.fmtaq.command.domain.task.TaskGroup;
import ys.prototype.fmtaq.command.domain.task.TaskSequence;

import java.util.UUID;

@Component
public class ModelFactory {

    public TaskSequence createSequence(UUID id) {
        return new TaskSequence(id);
    }

    public TaskGroup createGroup(UUID id) {
        return new TaskGroup(id);
    }

    public LinkedCommand createLinkedCommand(UUID id, UUID nextCommandId, String address, String body) {
        return new LinkedCommand(id, nextCommandId, address, body);
    }

    public GroupedCommand createGroupedCommand(UUID id, String address, String body) {
        return new GroupedCommand(id, address, body);
    }
}
