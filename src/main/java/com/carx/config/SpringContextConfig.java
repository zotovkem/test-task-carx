package com.carx.config;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Created by ZotovES on 05.04.2020
 * Конфигурация контекста Spring
 */
@ComponentScan(basePackages = "com.carx.*")
@Import(RedisConfig.class)
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.carx.repository")
@EntityScan(basePackages = "com.carx.domain.entity")
@PropertySource(value = "classpath:application.properties")
public class SpringContextConfig {

    @Bean
    public Gson getGson() {
        return new Gson();
    }
}
