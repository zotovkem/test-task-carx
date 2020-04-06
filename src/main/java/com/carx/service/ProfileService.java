package com.carx.service;

import com.carx.domain.entity.Profile;
import org.springframework.lang.NonNull;

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
    void createProfile(@NonNull Profile profile);

    /**
     * Получить профиль по уникальному идентификатору
     *
     * @param uuid уникальный идентификатор
     * @return профиль пользователь
     */
    Optional<Profile> findByUUID(@NonNull UUID uuid);
}
