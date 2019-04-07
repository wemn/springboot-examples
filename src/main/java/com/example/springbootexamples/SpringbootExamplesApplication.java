package com.example.springbootexamples;

import com.example.springbootexamples.repository.impl.CustomizedRespoistoryImpl;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
//import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomizedRespoistoryImpl.class)
public class SpringbootExamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExamplesApplication.class, args);
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected Hibernate5Module module() {
        Hibernate5Module module = new Hibernate5Module();
        // 序列化延迟加载对象的ID
        module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        return module;
    }

}
