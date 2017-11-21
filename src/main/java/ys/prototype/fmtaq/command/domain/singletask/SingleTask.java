package ys.prototype.fmtaq.command.domain.singletask;

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
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SingleTask extends Task {

    SingleTask(UUID id, String account, String serviceType, CommandSender commandSender) {
        super(id, account, serviceType, commandSender);
    }

    @Override
    public void start() {
        Optional<Command> commandOptional = getCommandSet().stream().findFirst();
        Command command = commandOptional.orElseThrow(this::emptyCommandSet);
        getCommandSender().send(command);
    }

    private FmtaqException emptyCommandSet() {
        return new FmtaqException(FmtaqErrorList.EMPTY_COMMAND_SET).set("task", this.toString());
    }

    void loadCommandList(List<SingleCommand> singleCommandList) {
        setCommandSet(new HashSet<>(singleCommandList));
    }
}
