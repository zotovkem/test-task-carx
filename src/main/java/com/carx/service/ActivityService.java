package com.carx.service;

import com.carx.domain.dto.ActivityDto;

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
}
