package ys.prototype.fmtaq.domain;

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
public class LinkedCommand extends Command {

    private UUID nextCommandId;

    @ManyToOne
    private Sequence task;

    public LinkedCommand(UUID id, String address, String body, Sequence task) {
        super(id, address, body);
        this.task = task;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
