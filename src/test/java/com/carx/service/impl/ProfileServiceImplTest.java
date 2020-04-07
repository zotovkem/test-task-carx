package com.carx.service.impl;

import com.carx.domain.dto.CountryCountProjection;
import com.carx.domain.entity.Profile;
import com.carx.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 07.04.2020
 */
@DisplayName("Тестирование Сервиса управления профилями")
class ProfileServiceImplTest {
    private ProfileServiceImpl profileService;
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        profileRepository = mock(ProfileRepository.class);
        profileService = new ProfileServiceImpl(profileRepository);
    }

    @Test
    @DisplayName("Добавление профиля")
    void createProfileTest() {
        Profile profile = Profile.builder()
                .uuid(UUID.randomUUID())
                .money(1)
                .country("RUS")
                .createDate(ZonedDateTime.now())
                .build();
        when(profileRepository.save(any())).thenReturn(profile);
        when(profileRepository.findByUuid(any())).thenReturn(Optional.empty());

        var resultProfile = profileService.createOrUpdateProfile(profile);
        assertNotNull(resultProfile);
        assertEquals(1, resultProfile.getMoney());

        verify(profileRepository).save(any());
        verify(profileRepository).findByUuid(any());
    }

    @Test
    @DisplayName("Обновление  профиля")
    void updateProfileTest() {
        var profile = Profile.builder()
                .uuid(UUID.randomUUID())
                .money(1)
                .country("RUS")
                .createDate(ZonedDateTime.now())
                .build();
        var profileToUpdate = Profile.builder()
                .uuid(UUID.randomUUID())
                .money(2)
                .country("RUS")
                .createDate(ZonedDateTime.now())
                .build();
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(profile));

        var resultProfile = profileService.createOrUpdateProfile(profileToUpdate);
        assertNotNull(resultProfile);
        assertEquals(2, resultProfile.getMoney());

        verify(profileRepository).save(any(Profile.class));
        verify(profileRepository).findByUuid(any(UUID.class));
    }

    @Test
    @DisplayName("Найти профиль по uuid")
    void findByUUIDTest() {
        UUID uuid = UUID.randomUUID();
        var profile = Profile.builder()
                .uuid(uuid)
                .money(1)
                .country("RUS")
                .createDate(ZonedDateTime.now())
                .build();
        when(profileRepository.findByUuid(any())).thenReturn(Optional.of(profile));

        var resultProfile = profileService.findByUUID(uuid);
        assertTrue(resultProfile.isPresent());
        assertEquals(1, resultProfile.get().getMoney());

        verify(profileRepository).findByUuid(any());
    }

    @Test
    @DisplayName("Получить список профилем с наибольшим балансом")
    void limitProfilesRichTest() {
        UUID uuid = UUID.randomUUID();
        var profile = Profile.builder()
                .uuid(uuid)
                .money(1)
                .country("RUS")
                .createDate(ZonedDateTime.now())
                .build();
        when(profileRepository.limitProfilesRich(any())).thenReturn(List.of(profile));

        var resultProfile = profileService.limitProfilesRich(10);
        assertEquals(1, resultProfile.size());

        verify(profileRepository).limitProfilesRich(any());
    }

    @Test
    @DisplayName("Получить новых профилей в стране за период")
    void countProfilesBetweenCreateDateTest() {
        when(profileRepository.countProfilesBetweenCreateDate(any(), any())).thenReturn(List.of(mock(CountryCountProjection.class)));

        var resultProfile = profileService.countProfilesBetweenCreateDate(ZonedDateTime.now().minusDays(1), ZonedDateTime.now());
        assertNotNull(resultProfile);

        verify(profileRepository).countProfilesBetweenCreateDate(any(), any());
    }
}
