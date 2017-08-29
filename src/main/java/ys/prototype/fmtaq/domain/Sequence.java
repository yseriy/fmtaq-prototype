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
public class Sequence extends Task {

    private UUID firstCommandId;

    @OneToMany(mappedBy = "task")
    private Set<LinkedCommand> commands;

    public void loadCommands(Set<LinkedCommand> commands) {
        this.commands = commands;
        this.commands.forEach(c -> c.setTask(this));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
