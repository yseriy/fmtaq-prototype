package ys.prototype.fmtaq.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TaskRowCallbackHandlerTest {

    @Test
    public void processRow() throws SQLException {
        PrintWriter printWriter = mock(PrintWriter.class);
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        ResultSet resultSet = mock(ResultSet.class);

        RowCallbackHandler rowCallbackHandler = new TaskRowCallbackHandler(printWriter, objectMapper);
        rowCallbackHandler.processRow(resultSet);

//        verify(objectMapper).writeValue(printWriter,);
    }
}
