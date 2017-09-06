package ys.prototype.fmtaq.command.domain.task.sequence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SyncTaskSequence extends TaskSequence {

    private String responseAddress;

    @Override
    public String getResponseAddress() {
        return responseAddress;
    }

    public void setResponseAddress(String responseAddress) {
        this.responseAddress = responseAddress;
    }
}
