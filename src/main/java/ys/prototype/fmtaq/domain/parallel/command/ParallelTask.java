package ys.prototype.fmtaq.domain.parallel.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelTask extends Task {

    private Integer commandCounter;

    public ParallelTask(UUID id, TaskStatus taskStatus, Integer commandCounter, CommandSendService sendService) {
        super(id, taskStatus, sendService);
        this.commandCounter = commandCounter;
    }

    @Override
    public void start() {
        if (getSendService() == null) {
            throw new RuntimeException("SendService not defined.");
        }

        getCommandSet().forEach(command -> getSendService().sendCommand(command));
    }
}
