package com.carx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by ZotovES on 05.04.2020
 * Конфигурация контекста Spring
 */
@Configuration
@ComponentScan(basePackages = "com.carx.*")
public class SpringContextConfig {
}
