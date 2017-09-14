package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SequenceCommandData {

    private UUID nextCommandId;

    public SequenceCommandData(UUID nextCommandId) {
        setNextCommandId(nextCommandId);
    }
}
