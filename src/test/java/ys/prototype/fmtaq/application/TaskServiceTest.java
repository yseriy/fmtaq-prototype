package ys.prototype.fmtaq.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.assembler.TaskAssembler;
import ys.prototype.fmtaq.application.assembler.TaskIdAssembler;
import ys.prototype.fmtaq.application.dto.CommandDTO;
import ys.prototype.fmtaq.application.dto.TaskDTO;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;
import ys.prototype.fmtaq.domain.singletask.SingleTask;
import ys.prototype.fmtaq.domain.task.Task;
import ys.prototype.fmtaq.domain.task.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy
@SpringBootTest(classes = {TaskService.class, ApplicationLogger.class})
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskAssembler taskAssembler;

    @MockBean
    private TaskIdAssembler taskIdAssembler;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void startAsyncTask() throws Exception {
        CommandDTO commandDTO = new CommandDTO("address", "body");
        List<CommandDTO> commandDTOList = new ArrayList<>();
        commandDTOList.add(commandDTO);
        TaskDTO taskDTO = new TaskDTO("SINGLE", commandDTOList);

        Task task = mock(SingleTask.class);
        when(task.getId()).thenReturn(UUID.randomUUID());
        when(taskAssembler.fromDTO(taskDTO)).thenReturn(task);


        TaskIdDTO taskIdDTO = taskService.startAsyncTask(taskDTO);

//        assertThat(taskIdDTO).isNotNull();
    }
}
