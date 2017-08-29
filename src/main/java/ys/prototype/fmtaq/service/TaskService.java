package ys.prototype.fmtaq.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskService {

    private final CommandService commandService;

    public TaskService(CommandService commandService) {
        this.commandService = commandService;
    }
}
