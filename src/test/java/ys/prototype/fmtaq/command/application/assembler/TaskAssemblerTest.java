package ys.prototype.fmtaq.command.application.assembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.command.application.dto.CommandDTO;
import ys.prototype.fmtaq.command.application.dto.TaskDTO;
import ys.prototype.fmtaq.command.domain.task.Task;
import ys.prototype.fmtaq.command.domain.task.TaskBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TaskAssemblerTest {

    @MockBean
    private TaskBuilderSelector taskBuilderSelector;

    @Test
    public void fromDTO() {
        String taskType = "TEST_TYPE";
        String account = "account";
        String serviceType = "service_Type";
        List<String> addressList = Arrays.asList("address_0", "address_1", "address_2");
        List<String> bodyList = Arrays.asList("body_0", "body_1", "body_2");

        List<CommandDTO> commandDTOList = Stream.iterate(0, i -> i + 1).limit(3)
                .map(i -> new CommandDTO(addressList.get(i), bodyList.get(i))).collect(Collectors.toList());

        TaskDTO taskDTO = new TaskDTO(taskType, account, serviceType, commandDTOList);

        Task task = mock(Task.class);
        TaskBuilder taskBuilder = mock(TaskBuilder.class);

        when(taskBuilderSelector.getTaskBuilderByTaskType(taskType)).thenReturn(taskBuilder);
        when(taskBuilder.setAccount(account)).thenReturn(taskBuilder);
        when(taskBuilder.setServiceType(serviceType)).thenReturn(taskBuilder);
        when(taskBuilder.build()).thenReturn(task);

        TaskAssembler taskAssembler = new TaskAssembler(taskBuilderSelector);
        Task taskFromDTO = taskAssembler.fromDTO(taskDTO);

        assertThat(taskFromDTO).isEqualTo(task);
        verify(taskBuilderSelector).getTaskBuilderByTaskType(taskType);
        verify(taskBuilder).setAccount(account);
        verify(taskBuilder).setServiceType(serviceType);
        verify(taskBuilder).addCommand(addressList.get(0), bodyList.get(0));
        verify(taskBuilder).addCommand(addressList.get(1), bodyList.get(1));
        verify(taskBuilder).addCommand(addressList.get(2), bodyList.get(2));
    }
}
