package ys.prototype.fmtaq.domain.sequence.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandSendService;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.command.Task;

import javax.persistence.Entity;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    private UUID firstCommandId;

    public SequenceTask(UUID id, TaskStatus taskStatus, CommandSendService sendService) {
        super(id, taskStatus, sendService);
    }

    @Override
    public void start() {
        Command firstCommand = getCommandSet().stream().filter(this::isFirstCommand).findFirst()
                .orElseThrow(this::exceptionSupplier);

        if (getCommandSet() == null) {
            throw new RuntimeException("SendService not defined.");
        }

        getSendService().sendCommand(firstCommand);
    }

    private Boolean isFirstCommand(Command command) {
        return command.getId() == firstCommandId;
    }

    private RuntimeException exceptionSupplier() {
        return new RuntimeException("cannot find first command.");
    }
}
