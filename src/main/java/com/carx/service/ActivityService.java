package com.carx.service;

import com.carx.domain.dto.ActivityDto;
import com.carx.domain.entity.Activity;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Created by ZotovES on 06.04.2020
 * Сервис управления сущностью активности пользователя
 */
public interface ActivityService {

    /**
     * Добавляет  в кэш информацию об активности
     *
     * @param activityDto дто активности пользователя
     */
    void addActivity(ActivityDto activityDto);

    /**
     * Поиск активностей по идентификатору пользователя за период
     *
     * @param uuid      идентификатор пользователя
     * @param beginDate начальная дата диапазона
     * @param endDate   конечная дата диапазона
     * @return список активностей
     */
    Collection<Activity> findActivityByUuidAndActivityDateBetween(@NonNull UUID uuid, @NonNull ZonedDateTime beginDate, @NonNull ZonedDateTime endDate);
}
