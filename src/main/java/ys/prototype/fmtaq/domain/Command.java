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

    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    @Version
    private Long version;
}
