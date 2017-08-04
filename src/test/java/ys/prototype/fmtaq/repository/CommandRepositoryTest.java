package ys.prototype.fmtaq.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.show-sql=true")
public class CommandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandRepository commandRepository;

    private UUID taskId;

    @Before
    public void setup() {
        Task task = new Task(TaskStatus.REGISTERED, TaskType.GROUP);

        entityManager.persist(task);
        entityManager.flush();

        taskId = task.getId();
    }

    @Test
    public void save() {
        Task task = entityManager.find(Task.class, taskId);

        Command command1 = new Command("test_address_1", "test_body_1", CommandStatus.REGISTERED, 0, task);
        Command command2 = new Command("test_address_1", "test_body_1", CommandStatus.REGISTERED, 1, task);

        entityManager.persist(command1);
        entityManager.persist(command2);
        entityManager.flush();

        assertThat(command1.getId()).isNotNull();
        assertThat(command2.getId()).isNotNull();

        Command command3 = entityManager.find(Command.class, command1.getId());
        Command command4 = entityManager.find(Command.class, command2.getId());

        assertThat(command3).isEqualTo(command1);
        assertThat(command4).isEqualTo(command2);
    }
}
