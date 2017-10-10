package ys.prototype.fmtaq.domain.task;

public interface TaskBuilder {

    Task build();

    void addCommand(String address, String body);
}
