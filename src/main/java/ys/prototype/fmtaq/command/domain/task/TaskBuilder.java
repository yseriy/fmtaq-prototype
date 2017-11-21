package ys.prototype.fmtaq.command.domain.task;

public interface TaskBuilder {

    Task build();

    TaskBuilder setAccount(String account);

    TaskBuilder setServiceType(String serviceType);

    void addCommand(String address, String body);
}
