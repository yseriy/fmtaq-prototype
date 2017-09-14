package ys.prototype.fmtaq.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class GeneralCommandData {

    @Enumerated(EnumType.STRING)
    private CommandStatus commandStatus;

    public GeneralCommandData(CommandStatus commandStatus) {
        setCommandStatus(commandStatus);
    }
}
