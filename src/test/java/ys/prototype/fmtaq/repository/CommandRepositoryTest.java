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
        Task task = new Task();
        task.setStatus(TaskStatus.REGISTERED);
        task.setType(TaskType.GROUP);

        entityManager.persist(task);
        entityManager.flush();

        taskId = task.getId();
    }

    @Test
    public void save() {
        Command command1 = new Command();
        command1.setAddress(new Address("test_address_1"));
        command1.setBody(new Body("test_body_1"));
        command1.setStatus(CommandStatus.REGISTERED);

        Command command2 = new Command();
        command2.setAddress(new Address("test_address_2"));
        command2.setBody(new Body("test_body_2"));
        command2.setStatus(CommandStatus.REGISTERED);

        Task task = entityManager.find(Task.class, taskId);
        entityManager.flush();

        command1.setTask(task);
        command2.setTask(task);

        entityManager.persist(command1);
        entityManager.persist(command2);
        entityManager.flush();

        assertThat(command1.getId()).isNotNull();
        assertThat(command2.getId()).isNotNull();
    }
}
