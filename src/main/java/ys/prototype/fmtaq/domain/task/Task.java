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
        setId(id);
        setStartTimestamp(LocalDateTime.now());
        setAccount(account);
        setServiceType(serviceType);
        setTaskStatus(TaskStatus.REGISTERED);
        setCommandSender(commandSender);
    }

    public abstract void start();

    protected void setCommandSet(Set<Command> commandSet) {
        commandSet.forEach(command -> command.setTask(this));
        this.commandSet = commandSet;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        setStatusTimestamp(LocalDateTime.now());
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
