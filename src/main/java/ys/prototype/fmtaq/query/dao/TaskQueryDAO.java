package ys.prototype.fmtaq.query.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ys.prototype.fmtaq.query.dto.TaskQueryDTO;

import java.util.List;

@Repository
public class TaskQueryDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TaskSqlMapper taskSqlMapper;

    public TaskQueryDAO(JdbcTemplate jdbcTemplate, TaskSqlMapper taskSqlMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskSqlMapper = taskSqlMapper;
    }

    public List<TaskQueryDTO> getById(Integer id) {
        String sql = "select t.id as t_id, t.status as t_status," +
                "            c.id as c_id, c.status as c_status, c.address as c_address, c.body as c_body" +
                "     from task t" +
                "     left join command c" +
                "            on t.id = c.task_id" +
                "     where t.id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, taskSqlMapper::map);
    }
}
