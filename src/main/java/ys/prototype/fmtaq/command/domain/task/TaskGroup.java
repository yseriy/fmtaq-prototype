package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TaskGroup extends Task {

    private Integer commandCounter;

    @OneToMany(mappedBy = "task")
    private Set<GroupedCommand> commands;

    public TaskGroup(UUID id) {
        super(id);
    }

    void loadCommands(Set<GroupedCommand> commands) {
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
