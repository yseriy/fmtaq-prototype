package ys.prototype.fmtaq.domain.sequencetask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_command_id", referencedColumnName = "id")
    private SequenceCommand firstCommand;

    public SequenceTask(UUID id, TaskStatus taskStatus, CommandSender commandSender) {
        super(id, taskStatus, commandSender);
    }

    @Override
    public void start() {
        getCommandSender().send(firstCommand);
    }

    void loadCommandList(List<SequenceCommand> sequenceCommandList) {
        ListIterator<SequenceCommand> sequenceCommandListIterator =
                sequenceCommandList.listIterator(sequenceCommandList.size());
        SequenceCommand nextCommand = null;

        while (sequenceCommandListIterator.hasPrevious()) {
            SequenceCommand sequenceCommand = sequenceCommandListIterator.previous();
            sequenceCommand.setNextCommand(nextCommand);
            nextCommand = sequenceCommand;
        }

        setFirstCommand(nextCommand);
        setCommandSet(new HashSet<>(sequenceCommandList));
    }
}
