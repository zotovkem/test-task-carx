package com.carx.repository;

import com.carx.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Created by ZotovES on 06.04.2020
 * Репозиторий управлениея профилем пользователя
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
