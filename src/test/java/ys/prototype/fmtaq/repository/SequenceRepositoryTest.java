package ys.prototype.fmtaq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.Command;
import ys.prototype.fmtaq.domain.LinkedCommand;
import ys.prototype.fmtaq.domain.Sequence;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SequenceRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired TaskRepository taskRepository;

    @Test
    public void save() {
        UUID taskId = UUID.randomUUID();
        Sequence task = new Sequence(taskId);
        UUID commandId = UUID.randomUUID();
        Set<LinkedCommand> commands = new HashSet<>();
        commands.add(new LinkedCommand(UUID.randomUUID(), UUID.randomUUID(), "address_1", "body1"));
        commands.add(new LinkedCommand(commandId, UUID.randomUUID(), "address_2", "body2"));
        commands.add(new LinkedCommand(UUID.randomUUID(), UUID.randomUUID(), "address_3", "body3"));

        task.loadCommands(commands);

        taskRepository.save(task);
        testEntityManager.flush();
        testEntityManager.clear();

        Command command = commandRepository.findOne(commandId);
    }
}
