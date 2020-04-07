package com.carx.service;

import com.carx.domain.entity.Profile;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 06.04.2020
 * Сервис управления профилем пользователя
 */
public interface ProfileService {
    /**
     * Создает новый профиль пользователя.
     *
     * @param profile профиль пользователя
     */
    Profile createOrUpdateProfile(@NonNull Profile profile);

    /**
     * Получить профиль по уникальному идентификатору
     *
     * @param uuid уникальный идентификатор
     * @return профиль пользователь
     */
    Optional<Profile> findByUUID(@NonNull UUID uuid);

    /**
     * Получить кл-во пользователей по странам зарегистрированных за период
     *
     * @param beginDate начальная дата периода
     * @param endDate   конеяная дата периода
     * @return список профилей пользователя
     */
    Map<String, Integer> countProfilesBetweenCreateDate(@NonNull ZonedDateTime beginDate, @NonNull ZonedDateTime endDate);

    /**
     * Получить список профилей пользователей самых богатых в стране
     *
     * @param limit кол-во богачей в стране
     * @return список профилей
     */
    public Collection<Profile> limitProfilesRich(@NonNull Integer limit);
}
