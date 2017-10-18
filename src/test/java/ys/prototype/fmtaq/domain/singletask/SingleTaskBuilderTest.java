package ys.prototype.fmtaq.domain.singletask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.domain.CommandStatus;
import ys.prototype.fmtaq.domain.TaskStatus;
import ys.prototype.fmtaq.domain.task.Command;
import ys.prototype.fmtaq.domain.task.CommandSender;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
public class SingleTaskBuilderTest {

    @MockBean
    private CommandSender commandSender;

    @Test
    public void build() {
        String account = "account";
        String serviceType = "service_type";
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2");
        TaskBuilder singleTaskBuilder = new SingleTaskBuilder(commandSender);
        singleTaskBuilder.setAccount(account).setServiceType(serviceType);

        for (int i = 0; i < 3; i++) {
            singleTaskBuilder.addCommand(addressList.get(i), bodyList.get(i));
        }

        Task task = singleTaskBuilder.build();

        assertThat(task).isNotNull();
        assertThat(task).isInstanceOf(SingleTask.class);
        assertThat(task.getId()).isNotNull();
        assertThat(task.getId()).isInstanceOf(UUID.class);
        assertThat(task.getAccount()).isEqualTo(account);
        assertThat(task.getServiceType()).isEqualTo(serviceType);
        assertThat(task.getTaskStatus()).isEqualTo(TaskStatus.REGISTERED);

        task.getCommandSet().forEach(this::checkCommand);

        assertThat(task.getCommandSet()).extracting("address", "body").containsExactlyInAnyOrder(
                tuple(addressList.get(0), bodyList.get(0)), tuple(addressList.get(1), bodyList.get(1)),
                tuple(addressList.get(2), bodyList.get(2)));
    }

    private void checkCommand(Command command) {
        assertThat(command).isNotNull();
        assertThat(command).isInstanceOf(SingleCommand.class);
        assertThat(command.getId()).isNotNull();
        assertThat(command.getId()).isInstanceOf(UUID.class);
        assertThat(command.getCommandStatus()).isEqualTo(CommandStatus.REGISTERED);
    }
}
