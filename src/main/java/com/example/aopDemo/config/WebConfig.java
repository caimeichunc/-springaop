package com.example.aopDemo.config;

import com.example.aopDemo.aspect.McInterceptor;
import com.example.aopDemo.aspect.annotation.McAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mc
 */
@Slf4j
@Configuration
public class WebConfig {

//    @Bean
//    @ConditionalOnMissingBean
//    public McAdvisor permissionAdvisor() {
//        return new McAdvisor(new McInterceptor());
//    }
}
