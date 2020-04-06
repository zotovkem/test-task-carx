package com.carx.config;

import com.google.gson.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.reflect.Type;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        return new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonElement json, Type type,
                                             JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

                JsonObject jsonObj = json.getAsJsonObject();

                JsonObject dateTime = jsonObj.getAsJsonObject("dateTime");
                JsonObject date = dateTime.getAsJsonObject("date");
                int year = date.get("year").getAsInt();
                int month = date.get("month").getAsInt();
                int day = date.get("day").getAsInt();

                JsonObject time = dateTime.getAsJsonObject("time");
                int hour = time.get("hour").getAsInt();
                int minute = time.get("minute").getAsInt();
                int second = time.get("second").getAsInt();
                int nano = time.get("nano").getAsInt();

                JsonObject zone = jsonObj.getAsJsonObject("zone");
                String id = zone.get("id").getAsString();

                return ZonedDateTime.of(year, month, day, hour, minute, second, nano, ZoneId.of(id));
            }
        }).create();
    }
}
