package ys.prototype.fmtaq.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;

import java.util.List;
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

    private UUID commandId;

    @Before
    public void setup() {
//        Task task = new Task(TaskStatus.REGISTERED, TaskType.GROUP);
//
//        Command command1 = new Command("test_address_3", "test_body_3", CommandStatus.REGISTERED, 6, task);
//        Command command2 = new Command("test_address_4", "test_body_4", CommandStatus.REGISTERED, 3, task);
//
//        entityManager.persist(task);
//        entityManager.persist(command2);
//        entityManager.persist(command1);
//        entityManager.flush();
//
//        taskId = task.getId();
//        commandId = command1.getId();
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

    @Test
    public void getNextCommand() {
//        Task task = entityManager.find(Task.class, taskId);
//        Command command = commandRepository.findOne(commandId);
//        List<Command> commandList = commandRepository.getNextCommand(task,CommandStatus.REGISTERED, new PageRequest(0, 1));
//
//        System.out.println(command.getTask());
//        System.out.println(commandList.get(0).getTask());

        Task task = new Task(TaskStatus.REGISTERED, TaskType.SEQUENCE);
        Task task1 = new Task(TaskStatus.REGISTERED, TaskType.SEQUENCE);

        entityManager.persist(task1);
        entityManager.flush();

        Command command = new Command("address1", "body", CommandStatus.REGISTERED, 0, task);
        Command command1 = new Command("address2", "body2", CommandStatus.REGISTERED, 0, task1);

        entityManager.persist(command);
        entityManager.flush();

        entityManager.persist(command1);
        entityManager.flush();

        entityManager.remove(command1);
        entityManager.flush();

        command.setStatusOk();

        entityManager.flush();
    }
}
