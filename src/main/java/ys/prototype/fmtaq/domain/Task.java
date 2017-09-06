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
public abstract class Task implements Synchronous {

    @Id
    private UUID id;

    @Embedded
    private Synchronous synchronous;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.REGISTERED;

    @Version
    private Long version;

    public Task(UUID id, Synchronous synchronous) {
        this.id = id;
        this.synchronous = synchronous;
    }

    @Override
    public String getResponseAddress() {
        return synchronous.getResponseAddress();
    }

    @Override
    public void setResponseAddress(String address) {
        synchronous.setResponseAddress(address);
    }
}
