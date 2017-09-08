package ys.prototype.fmtaq.command.domain.task.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.Task;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceTask extends Task {

    private UUID firstCommandId;

    @OneToMany(mappedBy = "sequenceTask", cascade = CascadeType.PERSIST)
    private Set<SequenceCommand> sequenceCommands;

    public SequenceTask(UUID id) {
        super(id);
    }

    @Override
    public Set<Command> getStartCommandSet() {
        Set<Command> commands = new HashSet<>();
        Command firstCommand = getSequenceCommands().stream().filter(this::isFirstCommand).findFirst()
                .orElseThrow(this::exceptionSupplier);
        commands.add(firstCommand);

        return commands;
    }

    private Boolean isFirstCommand(Command command) {
        return command.getId() == getFirstCommandId();
    }

    private RuntimeException exceptionSupplier() {
        return new RuntimeException("cannot find first command. task: " + this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
