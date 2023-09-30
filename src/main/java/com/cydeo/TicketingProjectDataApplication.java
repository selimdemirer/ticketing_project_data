package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication  // this covers @Configuration (also it covers @SpringBootConfiguration, @EnableAutoConfiguration and @ComponentScan)
public class  TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }

    //Write a method which return the object that you are trying to add in the container
    //Annotate this method with @Bean

    @Bean
    public ModelMapper mapper(){

        return new ModelMapper();

    }



}
