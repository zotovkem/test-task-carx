package com.carx.service.impl;

import com.carx.domain.dto.CountryCountProjection;
import com.carx.domain.entity.Profile;
import com.carx.repository.ProfileRepository;
import com.carx.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.isNull;

/**
 * @author Created by ZotovES on 06.04.2020
 * Реализация сервиса работы с профилем пользователя
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository repository;

    /**
     * Создает новый профиль пользователя.
     *
     * @param profile профиль пользователя
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Profile createOrUpdateProfile(@NonNull Profile profile) {
        if (isNull(profile.getCreateDate())) {
            profile.setCreateDate(ZonedDateTime.now());
        }
        return findByUUID(profile.getUuid())
                .map(updateProfileMoney(profile))
                .map(repository::save)
                .orElseGet(() -> repository.save(profile));
    }

    /**
     * Получить профиль по уникальному идентификатору
     *
     * @param uuid уникальный идентификатор
     * @return профиль пользователь
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> findByUUID(@NonNull UUID uuid) {
        return repository.findByUuid(uuid);
    }

    /**
     * Получить список профилей пользователей самых богатых в стране
     *
     * @param limit кол-во богачей в стране
     * @return список профилей
     */
    @Override
    @Transactional(readOnly = true)
    public Collection<Profile> limitProfilesRich(@NonNull Integer limit) {
        return repository.limitProfilesRich(limit);
    }

    /**
     * Получить кол-во пользователей по странам зарегистрированных за период
     *
     * @param beginDate начальная дата периода
     * @param endDate   конеяная дата периода
     * @return список профилей пользователя
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountryCountProjection> countProfilesBetweenCreateDate(@NonNull ZonedDateTime beginDate, @NonNull ZonedDateTime endDate) {
        return repository.countProfilesBetweenCreateDate(beginDate, endDate);
    }

    /**
     * Обновляет профиль пользователя
     *
     * @param profile профиль пользователя
     */
    private Function<Profile, Profile> updateProfileMoney(@NonNull Profile profile) {
        return p -> {
            p.setMoney(profile.getMoney());
            return p;
        };
    }
}
