package ys.prototype.fmtaq.domain;

import javax.persistence.Embeddable;

@Embeddable
public interface Synchronous {

    String getResponseAddress();

    void setResponseAddress(String address);
}
