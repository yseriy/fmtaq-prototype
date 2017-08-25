package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Command {

    @Id
    private UUID id;
    private String address;
    private String body;
    private CommandStatus status = CommandStatus.REGISTERED;

    @ManyToOne
    private Task task;

    public Command(UUID id, String address, String body) {
        this.id = id;
        this.address = address;
        this.body = body;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
