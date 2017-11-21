package ys.prototype.fmtaq.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.query.dto.CommandQueryDTO;
import ys.prototype.fmtaq.query.dto.TaskIdQueryDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@JdbcTest
@SpringBootTest(classes = CommandDAO.class)
public class CommandDAOTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommandDAO commandDAO;

    @Before
    public void setup() throws IOException {
        Path dbTestDataSqlFile = Paths.get("src/test/resources/test_data.sql");
        byte[] dbTestDataSqlByteArray = Files.readAllBytes(dbTestDataSqlFile);
        String dbTestDataSqlString = new String(dbTestDataSqlByteArray);
        jdbcTemplate.execute(dbTestDataSqlString);
    }

    @Test
    public void getCommandListByTaskId() {
        String taskId = "92354e79-560c-4bbb-8786-9087b82782e3";
        TaskIdQueryDTO taskIdQueryDTO = new TaskIdQueryDTO(taskId);

        List<CommandQueryDTO> commandQueryDTOList = commandDAO.getCommandListByTaskId(taskIdQueryDTO);

        System.out.println(commandQueryDTOList);
    }
}
