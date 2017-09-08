package ys.prototype.fmtaq.command.domain.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelCommand;
import ys.prototype.fmtaq.command.domain.task.impl.ParallelTask;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceCommand;
import ys.prototype.fmtaq.command.domain.task.impl.SequenceTask;

import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CommandRepository commandRepository;

    @Test
    public void findOne() {
        UUID commandId1 = UUID.randomUUID();
        UUID commandId2 = UUID.randomUUID();

        SequenceTask sequenceTask = new SequenceTask(UUID.randomUUID());
        Set<SequenceCommand> sequenceCommands = new HashSet<>();
        sequenceCommands.add(new SequenceCommand(UUID.randomUUID(), UUID.randomUUID(), "address_1", "body_1", sequenceTask));
        sequenceCommands.add(new SequenceCommand(commandId1, UUID.randomUUID(), "address_2", "body_2", sequenceTask));
        sequenceCommands.add(new SequenceCommand(UUID.randomUUID(), UUID.randomUUID(), "address_3", "body_3", sequenceTask));
        sequenceTask.setSequenceCommands(sequenceCommands);

        ParallelTask parallelTask = new ParallelTask(UUID.randomUUID());
        Set<ParallelCommand> parallelCommands = new HashSet<>();
        parallelCommands.add(new ParallelCommand(UUID.randomUUID(), "address_1", "body_1", parallelTask));
        parallelCommands.add(new ParallelCommand(commandId2, "address_2", "body_2", parallelTask));
        parallelCommands.add(new ParallelCommand(UUID.randomUUID(), "address_3", "body_3", parallelTask));
        parallelTask.setParallelCommands(parallelCommands);

        taskRepository.save(sequenceTask);
        taskRepository.save(parallelTask);
        entityManager.flush();
        entityManager.clear();

        Command command1 = commandRepository.findOne(commandId1);
        Command command2 = commandRepository.findOne(commandId2);
//        Command sequenceCommand = entityManager.find(SequenceCommand.class, commandId1);
//        Command parallelCommand = entityManager.find(ParallelCommand.class, commandId2);
//
//        System.out.println(sequenceCommand);
//        System.out.println(parallelCommand);
    }
}
