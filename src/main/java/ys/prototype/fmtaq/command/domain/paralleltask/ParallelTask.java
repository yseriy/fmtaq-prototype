package ys.prototype.fmtaq.command.domain.paralleltask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.FmtaqErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ParallelTask extends Task {

    private Integer commandCounter = 0;

    ParallelTask(UUID id, String account, String serviceType, CommandSender commandSender) {
        super(id, account, serviceType, commandSender);
    }

    @Override
    public void start() {
        getCommandSet().forEach(command -> getCommandSender().send(command));
    }

    void loadCommandList(List<ParallelCommand> parallelCommandList) {
        this.commandCounter = parallelCommandList.size();
        setCommandSet(new HashSet<>(parallelCommandList));
    }

    void reduceCommandCounter() {
        if (commandCounter <= 0) {
            throw illegalCommandCounterValue(commandCounter);
        }

        commandCounter--;
    }

    private FmtaqException illegalCommandCounterValue(Integer commandCounter) {
        return new FmtaqException(FmtaqErrorList.ILLEGAL_COMMAND_COUNTER_VALUE).set("task", this.toString())
                .set("counter", commandCounter.toString());
    }
}
