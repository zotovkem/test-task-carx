package com.carx.repository;

import com.carx.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by ZotovES on 05.04.2020
 * Репозиторий для управления сущностью активность пользователя
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
