package com.carx.repository;

import com.carx.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 06.04.2020
 * Репозиторий управлениея профилем пользователя
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    /**
     * Получить профиль по уникальному идентификатору
     *
     * @param uuid уникальный идентификатор
     * @return профиль пользователя
     */
    Optional<Profile> findByUuid(@NonNull UUID uuid);
}
