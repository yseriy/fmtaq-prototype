package ys.prototype.fmtaq.query.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import ys.prototype.fmtaq.query.dto.CommandQueryDTO;
import ys.prototype.fmtaq.query.dto.TaskQueryDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({TaskQueryDAO.class, TaskSqlMapper.class})
public class TaskQueryDAOTest {

    @Autowired
    private TaskQueryDAO taskQueryDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Integer taskId;

    @Before
    public void setup() {
        taskId = 1;

        jdbcTemplate.execute("create table task (id integer, status varchar(100)) ; create table command (id integer, task_id integer, status varchar(100), address varchar(255), body varchar(255))");
//        jdbcTemplate.execute("create table command (id integer, task_id integer, status varchar(100), address varchar(255), body varchar(255))");
        jdbcTemplate.update("insert into task (id, status) values (?, ?)", taskId, "OK");
        jdbcTemplate.update("insert into command (id, task_id, status, address, body) values (?, ?, ?, ?, ?)", 1, taskId, "OK", "address_1", "body_1");
        jdbcTemplate.update("insert into command (id, task_id, status, address, body) values (?, ?, ?, ?, ?)", 2, taskId, "OK", "address_2", "body_2");
    }

    @Test
    public void getById() throws Exception {
        List<TaskQueryDTO> taskQueryDTOList = taskQueryDAO.getById(taskId);
        assertThat(taskQueryDTOList).isNotNull();
        System.out.println(taskQueryDTOList);
//        String sql = "select t.id as t_id, t.status as t_status, c.id as c_id, c.status as c_status, c.address as c_address, c.body as c_body from task t left join command c on t.id = c.task_id";
//
//        RowMapper<TaskQueryDTO> mapper = (rs, i) -> {
//            TaskQueryDTO taskQueryDTO = new TaskQueryDTO(rs.getString("t_id"), rs.getString("t_status"));
//            CommandQueryDTO commandQueryDTO = new CommandQueryDTO(rs.getString("c_id"),
//                    rs.getString("c_status"), rs.getString("c_address"), rs.getString("c_body"));
//            System.out.println(commandQueryDTO);
//            return taskQueryDTO;
//        };
//
//        List<TaskQueryDTO> taskQueryDTOList = jdbcTemplate.query(sql, mapper);
//        System.out.println(taskQueryDTOList);
    }
}
