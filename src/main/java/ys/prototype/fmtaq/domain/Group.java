package ys.prototype.fmtaq.domain;

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
public class Group extends Task {

    private Integer commandCounter;

    @OneToMany(mappedBy = "task")
    private Set<GroupedCommand> commands;

    public Group(UUID id) {
        super(id);
    }

    public void loadCommands(Set<GroupedCommand> commands) {
        setCommands(commands);
        getCommands().forEach(command -> command.setTask(this));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
