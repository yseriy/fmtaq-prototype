package ys.prototype.fmtaq.domain.parallelcommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.command.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.command.Task;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.parallel.command.ParallelCommand;
import ys.prototype.fmtaq.domain.parallel.command.ParallelTask;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParallelCommandTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void save() {
        UUID taskId = UUID.randomUUID();
        UUID command1Id = UUID.randomUUID();
        UUID command2Id = UUID.randomUUID();
        UUID command3Id = UUID.randomUUID();

        Task task = new ParallelTask(taskId, TaskStatus.REGISTERED, 3, null);
        ParallelCommand command1 = new ParallelCommand(command1Id, "address_p_1",
                "body_p_1", CommandStatus.REGISTERED, task, null);
        ParallelCommand command2 = new ParallelCommand(command2Id, "address_p_2",
                "body_p_2", CommandStatus.REGISTERED, task, null);
        ParallelCommand command3 = new ParallelCommand(command3Id, "address_p_3",
                "body_p_3", CommandStatus.REGISTERED, task, null);

        entityManager.persist(command1);
        entityManager.persist(command2);
        entityManager.persist(command3);

        entityManager.flush();
        entityManager.clear();

        Command findCommand1 = entityManager.find(Command.class, command1Id);
        Command findCommand2 = entityManager.find(Command.class, command2Id);
        Command findCommand3 = entityManager.find(Command.class, command3Id);

        assertThat(findCommand1).isNotNull();
        assertThat(findCommand1.getId()).isEqualTo(command1Id);
        assertThat(findCommand1.getAddress()).isEqualTo("address_p_1");
        assertThat(findCommand1.getBody()).isEqualTo("body_p_1");
        assertThat(findCommand1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(findCommand1.getTask().getId()).isEqualTo(taskId);

        assertThat(findCommand2).isNotNull();
        assertThat(findCommand2.getId()).isEqualTo(command2Id);
        assertThat(findCommand2.getAddress()).isEqualTo("address_p_2");
        assertThat(findCommand2.getBody()).isEqualTo("body_p_2");
        assertThat(findCommand2.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(findCommand2.getTask().getId()).isEqualTo(taskId);

        assertThat(findCommand3).isNotNull();
        assertThat(findCommand3.getId()).isEqualTo(command3Id);
        assertThat(findCommand3.getAddress()).isEqualTo("address_p_3");
        assertThat(findCommand3.getBody()).isEqualTo("body_p_3");
        assertThat(findCommand3.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(findCommand3.getTask().getId()).isEqualTo(taskId);
    }
}