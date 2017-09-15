package ys.prototype.fmtaq.domain.sequence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.Task;
import ys.prototype.fmtaq.domain.TaskStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceCommandTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void save() {
        UUID taskId = UUID.randomUUID();
        UUID command1Id = UUID.randomUUID();
        UUID command2Id = UUID.randomUUID();
        UUID command3Id = UUID.randomUUID();

        Task task = new SequenceTask(taskId, TaskStatus.REGISTERED);
        SequenceCommand command1 = new SequenceCommand(command1Id, null, "address_1",
                "body_1", CommandStatus.REGISTERED, task);
        SequenceCommand command2 = new SequenceCommand(command2Id, command1, "address_2",
                "body_2", CommandStatus.REGISTERED, task);
        SequenceCommand command3 = new SequenceCommand(command3Id, command2, "address_3",
                "body_3", CommandStatus.REGISTERED, task);

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
        assertThat(findCommand1.getAddress()).isEqualTo("address_1");
        assertThat(findCommand1.getBody()).isEqualTo("body_1");
        assertThat(findCommand1.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(((SequenceCommand) findCommand1).getNextCommand()).isNull();

        assertThat(findCommand2).isNotNull();
        assertThat(findCommand2.getId()).isEqualTo(command2Id);
        assertThat(findCommand2.getAddress()).isEqualTo("address_2");
        assertThat(findCommand2.getBody()).isEqualTo("body_2");
        assertThat(findCommand2.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(((SequenceCommand) findCommand2).getNextCommand()).isNotNull();

        assertThat(findCommand3).isNotNull();
        assertThat(findCommand3.getId()).isEqualTo(command3Id);
        assertThat(findCommand3.getAddress()).isEqualTo("address_3");
        assertThat(findCommand3.getBody()).isEqualTo("body_3");
        assertThat(findCommand3.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
        assertThat(((SequenceCommand) findCommand3).getNextCommand()).isNotNull();
    }
}
