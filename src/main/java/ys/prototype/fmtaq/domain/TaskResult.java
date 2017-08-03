package ys.prototype.fmtaq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@ToString(exclude = {"id", "task"})
@EqualsAndHashCode(exclude = {"id", "task"})
@Entity
public class TaskResult {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Task task;

    @Enumerated
    private TaskResultStatus status;

    private String body;
}
