package ys.prototype.fmtaq.domain.paralleltask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

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

    public ParallelTask(UUID id, String account, String serviceType, CommandSender commandSender) {
        super(id, account, serviceType, commandSender);
    }

    @Override
    public void start() {
        getCommandSet().forEach(command -> getCommandSender().send(command));
    }

    void loadCommandList(List<ParallelCommand> parallelCommandList) {
        setCommandCounter(parallelCommandList.size());
        setCommandSet(new HashSet<>(parallelCommandList));
    }

    void reduceCommandCounter() {
        if (commandCounter > 0) commandCounter--;
    }
}
