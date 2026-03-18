package org.studentapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.studentapi.sample.BeanTypeFactory;

@SpringBootApplication
public class StudentApiApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StudentApiApplication.class, args);
        BeanTypeFactory beanTypeFactory = context.getBean(BeanTypeFactory.class);
        beanTypeFactory.printBeanType("type2");
    }

}

