package ys.prototype.fmtaq.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JacksonAutoConfiguration.class)
public class RowCallbackHandlerInjectorTest {

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Test
    public void injectTaskRowCallbackHandler() {
        PrintWriter printWriter = mock(PrintWriter.class);
        RowCallbackHandlerInjector rowCallbackHandlerInjector =
                new RowCallbackHandlerInjector(jackson2ObjectMapperBuilder);

        RowCallbackHandler taskRowCallbackHandler = rowCallbackHandlerInjector.injectTaskRowCallbackHandler(printWriter);

        assertThat(taskRowCallbackHandler).isNotNull();
        assertThat(taskRowCallbackHandler).isInstanceOf(TaskRowCallbackHandler.class);
    }
}
