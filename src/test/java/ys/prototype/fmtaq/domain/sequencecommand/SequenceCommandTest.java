package ys.prototype.fmtaq.domain.sequencecommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.sequencetask.SequenceCommand;
import ys.prototype.fmtaq.domain.sequencetask.SequenceTask;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.TaskRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceCommandTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void save() {
        UUID taskId = UUID.randomUUID();
        UUID command1Id = UUID.randomUUID();
        UUID command2Id = UUID.randomUUID();
        UUID command3Id = UUID.randomUUID();

        SequenceTask sequenceTask = new SequenceTask(taskId, TaskStatus.REGISTERED, null);
        SequenceCommand command1 = new SequenceCommand(command1Id, null, "address_1",
                "body_1", CommandStatus.REGISTERED, sequenceTask, null);
        SequenceCommand command2 = new SequenceCommand(command2Id, command1, "address_2",
                "body_2", CommandStatus.REGISTERED, sequenceTask, null);
        SequenceCommand command3 = new SequenceCommand(command3Id, command2, "address_3",
                "body_3", CommandStatus.REGISTERED, sequenceTask, null);
        sequenceTask.setFirstCommand(command3);

        Set<Command> commandSet = new HashSet<>();
        commandSet.add(command1);
        commandSet.add(command2);
        commandSet.add(command3);
        sequenceTask.setCommandSet(commandSet);

//        entityManager.persist(command1);
//        entityManager.persist(command2);
//        entityManager.persist(command3);

//        entityManager.persist(sequenceTask);
        taskRepository.save(sequenceTask);

//        entityManager.flush();
//        Command firstCommand = sequenceTask.getFirstCommand();

        entityManager.flush();
        entityManager.clear();
//
//        Command findCommand1 = entityManager.find(Command.class, command1Id);
//        firstCommand = ((SequenceTask) findCommand1.getTask()).getFirstCommand();
//        System.out.println(firstCommand.getBody());

//        Command findCommand2 = entityManager.find(Command.class, command2Id);
//        Command findCommand3 = entityManager.find(Command.class, command3Id);
//
//        assertThat(findCommand1).isNotNull();
//        assertThat(findCommand1.getId()).isEqualTo(command1Id);
//        assertThat(findCommand1.getAddress()).isEqualTo("address_1");
//        assertThat(findCommand1.getBody()).isEqualTo("body_1");
//        assertThat(findCommand1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
//        assertThat(((SequenceCommand) findCommand1).getNextCommand()).isNull();
//
//        assertThat(findCommand2).isNotNull();
//        assertThat(findCommand2.getId()).isEqualTo(command2Id);
//        assertThat(findCommand2.getAddress()).isEqualTo("address_2");
//        assertThat(findCommand2.getBody()).isEqualTo("body_2");
//        assertThat(findCommand2.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
//        assertThat(((SequenceCommand) findCommand2).getNextCommand()).isNotNull();
//
//        assertThat(findCommand3).isNotNull();
//        assertThat(findCommand3.getId()).isEqualTo(command3Id);
//        assertThat(findCommand3.getAddress()).isEqualTo("address_3");
//        assertThat(findCommand3.getBody()).isEqualTo("body_3");
//        assertThat(findCommand3.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
//        assertThat(((SequenceCommand) findCommand3).getNextCommand()).isNotNull();
    }
}
