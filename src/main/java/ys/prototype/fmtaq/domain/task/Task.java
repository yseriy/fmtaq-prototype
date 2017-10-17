package ys.prototype.fmtaq.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.TaskStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private LocalDateTime startTimestamp;
    private LocalDateTime statusTimestamp;
    private String account;
    private String serviceType;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Version
    private Long version;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<Command> commandSet;

    protected Task(UUID id, String account, String serviceType, CommandSender commandSender) {
        this.id = id;
        this.startTimestamp = this.statusTimestamp = LocalDateTime.now();
        this.account = account;
        this.serviceType = serviceType;
        this.taskStatus = TaskStatus.REGISTERED;
        this.commandSender = commandSender;
    }

    public abstract void start();

    public void setCommandSet(Set<Command> commandSet) {
        commandSet.forEach(command -> command.setTask(this));
        this.commandSet = commandSet;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.statusTimestamp = LocalDateTime.now();
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
