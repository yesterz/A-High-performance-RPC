package com.yesterz.basic.controller;

import com.yesterz.basic.service.BasicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yesterz")
public class BasicController {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(BasicController.class);
        BasicService basicService = context.getBean(BasicService.class);
        basicService.testSaveUser();
    }
}
