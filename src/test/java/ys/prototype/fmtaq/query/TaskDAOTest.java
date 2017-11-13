package ys.prototype.fmtaq.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.query.dto.TaskIdRequestDTO;
import ys.prototype.fmtaq.query.dto.UserInfoDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@JdbcTest
@SpringBootTest(classes = JacksonAutoConfiguration.class)
public class TaskDAOTest {

    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Before
    public void setup() throws IOException {
        Path dbTestDataSqlFile = Paths.get("src/test/resources/test_data.sql");
        byte[] dbTestDataSqlByteArray = Files.readAllBytes(dbTestDataSqlFile);
        String dbTestDataSqlString = new String(dbTestDataSqlByteArray, UTF_8_CHARSET);
        jdbcTemplate.execute(dbTestDataSqlString);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    public void getTaskListByUsrInfo() {
        String account = "account";
        String serviceType = "service_type";
        UserInfoDTO userInfoDTO = new UserInfoDTO(account, serviceType);

        TaskDAO taskDAO = new TaskDAO(jdbcTemplate, jackson2ObjectMapperBuilder);
        taskDAO.getTaskListByUserInfo(printWriter, userInfoDTO);

        System.out.println(stringWriter.toString());
    }

    @Test
    public void getTaskListByTaskId() {
        String task_id = "92354e79-560c-4bbb-8786-9087b82782e3";
        TaskIdRequestDTO taskIdRequestDTO = new TaskIdRequestDTO(task_id);

        TaskDAO taskDAO = new TaskDAO(jdbcTemplate, jackson2ObjectMapperBuilder);
        taskDAO.getTaskListByTaskId(printWriter, taskIdRequestDTO);

        System.out.println(stringWriter.toString());
    }
}
