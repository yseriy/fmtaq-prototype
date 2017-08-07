package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.*;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void save() {
        TaskStatus taskStatus = TaskStatus.REGISTERED;
        TaskType taskType = TaskType.GROUP;
        String commandAddress = "test_address_";
        String commandBody = "test_body_";
        CommandStatus status = CommandStatus.REGISTERED;

        Task task = new Task(taskStatus, taskType);
        Set<Command> commandSet = new HashSet<>();

        for (Integer i = 0; i < 10; i++) {
            commandSet.add(new Command(commandAddress + i, commandBody + i, status, i));
        }

        task.setCommands(commandSet);

        taskRepository.save(task);
        entityManager.flush();
    }
}
