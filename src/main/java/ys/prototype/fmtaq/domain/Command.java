package ys.prototype.fmtaq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@Entity
public class Command {

    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = AddressConverter.class)
    private Address address;

    @Convert(converter = BodyConverter.class)
    private Body body;

    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    @ManyToOne
    private Task task;
}
