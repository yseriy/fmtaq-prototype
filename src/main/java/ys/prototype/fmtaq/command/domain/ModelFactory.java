package ys.prototype.fmtaq.command.domain;

import org.springframework.stereotype.Component;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelCommand;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelTask;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceCommand;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceTask;

import java.util.UUID;

@Component
public class ModelFactory {

    public SequenceTask createSequenceTask(UUID id) {
        return new SequenceTask(id);
    }

    public ParallelTask createParallelTask(UUID id) {
        return new ParallelTask(id);
    }

    public SequenceCommand createSequenceCommand(UUID id, UUID nextCommandId, String address, String body,
                                                 SequenceTask task) {
        return new SequenceCommand(id, nextCommandId, address, body, task);
    }

    public ParallelCommand createParallelCommand(UUID id, String address, String body, ParallelTask task) {
        return new ParallelCommand(id, address, body, task);
    }
}
