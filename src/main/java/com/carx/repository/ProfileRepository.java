package com.carx.repository;

import com.carx.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.ZonedDateTime;
import java.util.*;

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

    /**
     * Получить кол-во пользователей по странам зарегистрированных за период
     *
     * @param beginDate начальная дата периода
     * @param endDate   конеяная дата периода
     * @return список профилей пользователя
     */
    @Query(nativeQuery = true, value = "" +
            "select  p.country, count(p.uuid)" +
            "from carx.profile p " +
            "where p.create_date between :beginDate and :endDate " +
            "group by p.country")
    Map<String, Integer> countProfilesBetweenCreateDate(@NonNull @Param("beginDate") ZonedDateTime beginDate,
                                                        @NonNull @Param("endDate") ZonedDateTime endDate);

    @Query(nativeQuery = true, value = "" +
            "select * " +
            "from ( " +
            "select p.*, row_number() over (partition by country order by money desc) as r " +
            "from carx.profile p) p " +
            "where p.r <= :limit")
    Collection<Profile> limitProfilesRich(@NonNull @Param("limit") Integer limit);
}
