package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SequenceIndex {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID commandId;
    private UUID nextCommandId;

    @ManyToOne
    private SequenceMetadata sequenceMetadata;

    SequenceIndex(UUID commandId, UUID nextCommandId, SequenceMetadata sequenceMetadata) {
        this.commandId = commandId;
        this.nextCommandId = nextCommandId;
        this.sequenceMetadata = sequenceMetadata;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "-" + getId();
    }
}
