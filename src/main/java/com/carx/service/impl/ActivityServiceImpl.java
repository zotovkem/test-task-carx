package com.carx.service.impl;

import com.carx.domain.dto.ActivityDto;
import com.carx.domain.entity.Activity;
import com.carx.domain.entity.Profile;
import com.carx.repository.ActivityRepository;
import com.carx.service.ActivityService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

/**
 * @author Created by ZotovES on 05.04.2020
 * Реализация сервиса работы с активность пользователя
 */
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private static final String ACTIVITY_REDIS_KEY = "ACTIVITY";
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
        Long count = jedisClient.lpush(ACTIVITY_REDIS_KEY, mapper.toJson(activityDto));

        if (count > countRecordCacheSize) {
            new Thread(saveBatch()).start();
        }
    }

    /**
     * Поиск активностей по идентификатору пользователя за период
     *
     * @param uuid      идентификатор пользователя
     * @param beginDate начальная дата диапазона
     * @param endDate   конечная дата диапазона
     * @return список активностей
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Activity> findActivityByUuidAndActivityDateBetween(@NonNull UUID uuid, @NonNull ZonedDateTime beginDate, @NonNull ZonedDateTime endDate) {
        return repository.findActivityByUuidAndActivityDateBetween(uuid, beginDate, endDate);
    }

    /**
     * Сохраняет в БД определенное кол-во записей
     */
    private Runnable saveBatch() {
        return new Runnable() {
            @Transactional(rollbackFor = Exception.class)
            public void run() {
                var activityList = new ArrayList<Activity>();
                for (int i = 0; i < countRecordCacheSize; i++) {
                    Optional.of(jedisClient.rpop(ACTIVITY_REDIS_KEY))
                            .map(json -> mapper.fromJson(json, ActivityDto.class))
                            .filter(a -> nonNull(a.getUuid()))
                            .map(mappingToEntity())
                            .ifPresent(activityList::add);
                }

                repository.saveAll(activityList);
            }
        };
    }


    /**
     * Маппер из дто в сущность активности
     */
    private Function<ActivityDto, Activity> mappingToEntity() {
        return activityDto -> Activity.builder()
                .profile(Profile.builder()
                        .uuid(activityDto.getUuid())
                        .build())
                .activityDate(activityDto.getActivityDate())
                .activity(ofNullable(activityDto.getActivity()).orElse(0))
                .build();
    }
}
