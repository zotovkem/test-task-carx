package com.carx.repository;

import com.carx.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
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
    Optional<Profile> findByUuid(@NonNull @Param("uuid") UUID uuid);

    /**
     * Получить список профилей пользователей по списку идентификаторов
     *
     * @param uuids список идентификаторов
     * @return список профилей пользователей
     */
    @Query(value = "" +
            "select p " +
            "from Profile p " +
            "where uuid in :uuids")
    Collection<Profile> findAllByUuidIn(@Param("uuids") List<UUID> uuids);
}
