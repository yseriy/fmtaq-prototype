package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "task", cascade = CascadeType.PERSIST)
    private Set<Command> commands = new HashSet<>();

    public Task(UUID id) {
        this.id = id;
    }

    void loadCommands(Set<Command> commands) {
        this.commands = commands;
        this.commands.forEach(command -> command.setTask(this));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
