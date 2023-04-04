package kr.onthelive.basic.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.onthelive.basic.ws")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
