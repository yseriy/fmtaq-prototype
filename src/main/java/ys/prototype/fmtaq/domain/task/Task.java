package ys.prototype.fmtaq.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ys.prototype.fmtaq.domain.CommandSendService;
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
    private CommandSendService sendService;

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Version
    private Long version;

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<Command> commandSet;

    public Task(UUID id, TaskStatus taskStatus, CommandSendService sendService) {
        this.id = id;
        this.taskStatus = taskStatus;
        this.sendService = sendService;
    }

    public abstract void start();
}
