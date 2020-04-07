package com.carx.service;

import com.carx.domain.entity.Profile;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
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
     * Получить список профилей пользователей по списку идентификаторов
     *
     * @param uuids список идентификаторов
     * @return список профилей
     */
    Collection<Profile> findAllByUuidIn(List<UUID> uuids);
}
