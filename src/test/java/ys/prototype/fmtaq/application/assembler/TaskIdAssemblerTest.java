package ys.prototype.fmtaq.application.assembler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.application.dto.TaskIdDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TaskIdAssemblerTest {

    @Test
    public void toDTO() {
        UUID taskId = UUID.randomUUID();
        TaskIdAssembler taskIdAssembler = new TaskIdAssembler();
        TaskIdDTO taskIdDTO = taskIdAssembler.toDTO(taskId);

        assertThat(taskIdDTO).isNotNull();
        assertThat(taskIdDTO).isInstanceOf(TaskIdDTO.class);
        assertThat(taskIdDTO.getId()).isEqualTo(taskId.toString());
    }
}
