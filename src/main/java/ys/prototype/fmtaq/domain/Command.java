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
abstract class Command {

    @Id
    private UUID id;

    @Version
    private Long version;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;

    Command(UUID id, Task task) {
        setId(id);
        setTask(task);
    }
}
