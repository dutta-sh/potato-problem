package org.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//initializes as an web application
//HTTP call listening on
//inbuilt tomcat port 8080

@SpringBootApplication
@Configuration
@PropertySource("classpath:app.properties")
@Log4j2
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args).registerShutdownHook();
        log.info("--------------------- started application --------------------------");
    }
}
