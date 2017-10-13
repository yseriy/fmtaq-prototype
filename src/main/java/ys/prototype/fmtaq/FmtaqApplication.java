package ys.prototype.fmtaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FmtaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmtaqApplication.class, args);
    }
}
