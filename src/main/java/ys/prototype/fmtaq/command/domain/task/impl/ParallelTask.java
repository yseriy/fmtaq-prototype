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
public class ParallelTask extends Task {

    private Integer commandCounter;

    @OneToMany(mappedBy = "parallelTask", cascade = CascadeType.PERSIST)
    private Set<ParallelCommand> parallelCommands;

    public ParallelTask(UUID id) {
        super(id);
    }

    @Override
    public Set<Command> getStartCommandSet() {
        return new HashSet<>(getParallelCommands());
    }

    void decreaseCommandCounter() {
        setCommandCounter(getCommandCounter() - 1);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
