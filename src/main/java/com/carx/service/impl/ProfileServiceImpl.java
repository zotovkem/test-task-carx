package com.carx.service.impl;

import com.carx.domain.entity.Profile;
import com.carx.repository.ProfileRepository;
import com.carx.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
    public void createProfile(@NonNull Profile profile) {
        repository.save(profile);
    }


}
