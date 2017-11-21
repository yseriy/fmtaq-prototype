package ys.prototype.fmtaq.command.domain.sequencetask;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.FmtaqErrorList;
import ys.prototype.fmtaq.command.domain.FmtaqException;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    private UUID firstCommandId;

    SequenceTask(UUID id, String account, String serviceType, CommandSender commandSender) {
        super(id, account, serviceType, commandSender);
    }

    @Override
    public void start() {
        getCommandSender().send(getFirstCommand());
    }

    private Command getFirstCommand() {
        return getCommandSet().stream().filter(command -> command.getId().equals(firstCommandId)).findFirst()
                .orElseThrow(this::cannotFindFirstCommand);
    }

    private FmtaqException cannotFindFirstCommand() {
        return new FmtaqException(FmtaqErrorList.CANNOT_FIND_FIRST_COMMAND)
                .set("first command id", firstCommandId.toString()).set("command set", getCommandSet().toString());
    }

    void loadCommandList(List<SequenceCommand> sequenceCommandList) {
        createSequenceCommandLinkedList(sequenceCommandList);
        setFirstCommandId(getFirstCommandIdFromList(sequenceCommandList));
        setCommandSet(new HashSet<>(sequenceCommandList));
    }

    private void createSequenceCommandLinkedList(List<SequenceCommand> sequenceCommandList) {
        ListIterator<SequenceCommand> sequenceCommandListIterator =
                sequenceCommandList.listIterator(sequenceCommandList.size());
        SequenceCommand nextCommand = null;

        while (sequenceCommandListIterator.hasPrevious()) {
            SequenceCommand sequenceCommand = sequenceCommandListIterator.previous();
            sequenceCommand.setNextCommand(nextCommand);
            nextCommand = sequenceCommand;
        }
    }

    private UUID getFirstCommandIdFromList(List<SequenceCommand> sequenceCommandList) {
        try {
            return sequenceCommandList.get(0).getId();
        } catch (IndexOutOfBoundsException e) {
            throw emptyIncomingCommandList(e);
        }
    }

    private FmtaqException emptyIncomingCommandList(Throwable cause) {
        return new FmtaqException(FmtaqErrorList.EMPTY_INCOMING_COMMAND_LIST, cause);
    }
}
