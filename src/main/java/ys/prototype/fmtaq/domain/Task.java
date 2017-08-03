package ys.prototype.fmtaq.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Data
@ToString(exclude = {"id", "taskResult"})
@EqualsAndHashCode(exclude = {"id", "taskResult"})
@Entity
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TaskResult taskResult;

    private String body;
}
