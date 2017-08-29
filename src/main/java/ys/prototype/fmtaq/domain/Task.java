package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Task {

    @Id
    private UUID id;
    private TaskStatus status = TaskStatus.REGISTERED;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Command> commands = new HashSet<>();

    @Version
    private Long version;

    public Task(UUID id) {
        this.id = id;
    }

    public void loadCommands(Set<Command> commands) {
        this.commands = commands;
        this.commands.forEach(command -> command.setTask(this));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
