package ys.prototype.fmtaq.command.domain.task.group;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.Task;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TaskGroup extends Task {

    private Integer commandCounter;

    @OneToMany(mappedBy = "task")
    private Set<CommandGroup> commands;

    public TaskGroup(UUID id) {
        super(id);
    }

    @Override
    public Set<Command> getStartCommands() {
        return new HashSet<>(getCommands());
    }

    public void loadCommands(Set<CommandGroup> commands) {
        setCommands(commands);
        getCommands().forEach(command -> command.setTask(this));
    }

    void decreaseCommandCounter() {
        setCommandCounter(getCommandCounter() - 1);
    }

    Boolean isLastCommand() {
        return getCommandCounter() <= 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
