package ys.prototype.fmtaq.command.domain.paralleltask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.domain.task.Command;
import ys.prototype.fmtaq.command.domain.task.CommandRepository;
import ys.prototype.fmtaq.command.domain.task.CommandSender;
import ys.prototype.fmtaq.command.domain.task.Task;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParallelCommandJpaTest {

    @MockBean
    private CommandSender commandSender;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommandRepository commandRepository;

    @Test
    public void save() {
        UUID taskId = UUID.randomUUID();
        UUID commandId1 = UUID.randomUUID();
        UUID commandId2 = UUID.randomUUID();
        UUID commandId3 = UUID.randomUUID();

        String account = "account";
        String serviceType = "service_type";
        Integer commandCounter = 3;
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2", "address_3");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2", "body_3");

        Task task = new ParallelTask(taskId, account, serviceType, commandSender);
        ParallelTask parallelTask = (ParallelTask) task;
        parallelTask.setCommandCounter(commandCounter);
        Command command1 = new ParallelCommand(commandId1, addressList.get(0), bodyList.get(0), commandSender);
        Command command2 = new ParallelCommand(commandId2, addressList.get(1), bodyList.get(1), commandSender);
        Command command3 = new ParallelCommand(commandId3, addressList.get(2), bodyList.get(2), commandSender);

        Set<Command> commandSet = new HashSet<>();
        commandSet.add(command1);
        commandSet.add(command2);
        commandSet.add(command3);
        task.setCommandSet(commandSet);

        entityManager.persist(task);
        entityManager.flush();
        entityManager.clear();

        Command commandFromDb = commandRepository.findOne(commandId2);
        commandFromDb.setCommandSender(commandSender);

        assertThat(commandFromDb).isNotNull();
        assertThat(commandFromDb).isInstanceOf(ParallelCommand.class);
        assertThat(commandFromDb).isEqualToComparingFieldByField(command2);
    }
}
