package ys.prototype.fmtaq.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
public abstract class Task {

    @Transient
    private CommandSender commandSender;

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Version
    private Long version;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<Command> commandSet;

    public Task(UUID id, TaskStatus taskStatus, CommandSender commandSender) {
        setId(id);
        setTaskStatus(taskStatus);
        setCommandSender(commandSender);
    }

    public abstract void start();

    public void setCommandSet(Set<Command> commandSet) {
        commandSet.forEach(command -> command.setTask(this));
        this.commandSet = commandSet;
    }
}
