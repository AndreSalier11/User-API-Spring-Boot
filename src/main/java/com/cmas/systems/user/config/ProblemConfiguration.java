package com.cmas.systems.user.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ProblemConfiguration implements InitializingBean {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void afterPropertiesSet() {
        objectMapper.registerModules(
                new ProblemModule()
        );
    }
}