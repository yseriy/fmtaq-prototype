package ys.prototype.fmtaq.command.domain.task.sequence;

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
public class TaskSequence extends Task {

    private UUID firstCommandId;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<CommandSequence> commands;

    public TaskSequence(UUID id) {
        super(id);
    }

    @Override
    public Set<Command> getStartCommands() {
        Set<Command> commands = new HashSet<>();
        commands.add(getCommands().stream().filter(this::isFirstCommand).findFirst().orElseThrow(this::exceptionSupplier));

        return commands;
    }

    private Boolean isFirstCommand(Command command) {
        return command.getId() == getFirstCommandId();
    }

    private RuntimeException exceptionSupplier() {
        return new RuntimeException("cannot find first command. task: " + this);
    }

    public void loadCommands(Set<CommandSequence> commands) {
        setCommands(commands);
        getCommands().forEach(command -> command.setTask(this));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
