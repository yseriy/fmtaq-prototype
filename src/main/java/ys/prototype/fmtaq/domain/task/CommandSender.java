package ys.prototype.fmtaq.domain.task;

import ys.prototype.fmtaq.domain.task.Command;

public interface CommandSender {
    void send(Command command);
}
