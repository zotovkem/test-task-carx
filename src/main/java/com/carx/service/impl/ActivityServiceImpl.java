package com.carx.service.impl;

import com.carx.domain.dto.ActivityDto;
import com.carx.domain.entity.Activity;
import com.carx.repository.ActivityRepository;
import com.carx.service.ActivityService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author Created by ZotovES on 05.04.2020
 * Реализация сервиса работы с активность пользователя
 */
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final Jedis jedisClient;
    private final Gson mapper;
    private final ActivityRepository repository;

    @Value("${app.cacheSize:10}")
    private Integer countRecordCacheSize;

    /**
     * Добавляет  в кэш информацию об активности
     *
     * @param activityDto дто активности пользователя
     */
    @Override
    public void addActivity(ActivityDto activityDto) {
        activityDto.setActivityDate(ZonedDateTime.now());
        Long count = jedisClient.lpush("ACTIVITY", mapper.toJson(activityDto));

        if (count > countRecordCacheSize) {
            new Thread(this::saveBatch).start();
        }
    }

    /**
     * Сохраняет в БД определенное кол-во записей
     */
    private void saveBatch() {
        var activity = IntStream.of(countRecordCacheSize)
                .mapToObj(i -> jedisClient.rpop("ACTIVITY"))
                .filter(Objects::nonNull)
                .map(json -> mapper.fromJson(json, Activity.class))
                .collect(toList());

        repository.saveAll(activity);
    }
}
