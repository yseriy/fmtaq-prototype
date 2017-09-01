package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    void loadCommands(Set<CommandSequence> commands) {
        setCommands(commands);
        getCommands().forEach(command -> command.setTask(this));
    }

    public TaskSequence(UUID id) {
        super(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
