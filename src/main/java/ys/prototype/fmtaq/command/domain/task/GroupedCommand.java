package ys.prototype.fmtaq.command.domain.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GroupedCommand extends Command {

    @ManyToOne
    private TaskGroup task;

    public GroupedCommand(UUID id, String address, String body) {
        super(id, address, body);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
