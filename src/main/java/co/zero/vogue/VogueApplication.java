package co.zero.vogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by htenjo on 5/29/16.
 */
@SpringBootApplication
public class VogueApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VogueApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(VogueApplication.class, args);
    }
}