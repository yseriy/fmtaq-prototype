package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance
public abstract class Command {

    @Id
    private UUID id;
    private String address;
    private String body;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private CommandStatus status = CommandStatus.REGISTERED;

    public Command(String address, String body) {
        this.address = address;
        this.body = body;
    }
}
