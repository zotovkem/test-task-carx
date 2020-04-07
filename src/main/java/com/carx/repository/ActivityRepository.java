package com.carx.repository;

import com.carx.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Created by ZotovES on 05.04.2020
 * Репозиторий для управления сущностью активность пользователя
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    /**
     * Поиск активностей по идентификатору пользователя за период
     *
     * @param uuid      идентификатор пользователя
     * @param beginDate начальная дата диапазона
     * @param endDate   конечная дата диапазона
     * @return список активностей
     */
    @Query("" +
            "select a " +
            "from Activity a " +
            "inner join fetch a.profile p " +
            "where p.uuid = :uuid and a.activityDate between :beginDate and :endDate " +
            "order by a.activityDate desc")
    Collection<Activity> findActivityByUuidAndActivityDateBetween(@Param("uuid") @NonNull UUID uuid,
                                                                  @Param("beginDate") @NonNull ZonedDateTime beginDate,
                                                                  @Param("endDate") @NonNull ZonedDateTime endDate);
}
