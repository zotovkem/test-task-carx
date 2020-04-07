package com.carx.controller;

import com.carx.domain.dto.ProfileDto;
import com.carx.domain.entity.Profile;
import com.carx.service.ProfileService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import spark.QueryParamsMap;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static spark.Spark.*;

/**
 * @author Created by ZotovES on 06.04.2020
 * Контроллер управления профилем пользователя
 */
@Controller
public class ProfileController {
    private final ProfileService profileService;
    private final Gson mapper;

    public ProfileController(ProfileService profileService, Gson mapper) {
        this.profileService = profileService;
        this.mapper = mapper;

        after((request, response) -> {
            response.type("application/json");
        });
        createOrUpdateProfile();
        getProfileByUuid();
    }

    /**
     * Создает профиль пользователя
     */
    private void createOrUpdateProfile() {
        post("/profile", (req, res) -> ofNullable(req.body())
                .map(json -> mapper.fromJson(json, ProfileDto.class))
                .map(mappingToEntity())
                .map(profileService::createOrUpdateProfile)
                .map(mappingToDto())
                .map(dto -> mapper.toJson(dto, ProfileDto.class))
                .orElseGet(() -> {
                    halt(404);
                    return null;
                }));
    }

    /**
     * Получить профиль пользователя по уникальному идентификатору пользователя
     */
    private void getProfileByUuid() {
        get("/profile", (req, res) -> ofNullable(req.queryMap("uuid"))
                .map(QueryParamsMap::value)
                .map(parseStringOrNull())
                .map(profileService::findByUUID)
                .map(Optional::get)
                .map(mappingToDto())
                .map(dto -> mapper.toJson(dto, ProfileDto.class))
                .orElseGet(() -> {
                    halt(404);
                    return null;
                }));
    }

    /**
     * Парсит строку в uuid  или возвращает null
     */
    private Function<String, UUID> parseStringOrNull() {
        return str -> {
            try {
                return UUID.fromString(str);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        };
    }

    /**
     * Маппер из дто в сущность профиля
     */
    private Function<ProfileDto, Profile> mappingToEntity() {
        return profileDto -> Profile.builder()
                .uuid(ofNullable(profileDto.getUuid())
                        .orElseThrow(() -> new IllegalArgumentException("Не указан уникальный идентификатор пользователя")))
                .country(ofNullable(profileDto.getCountry())
                        .orElseThrow(() -> new IllegalArgumentException("Не страна пользователя")))
                .money(ofNullable(profileDto.getMoney()).orElse(0))
                .build();
    }

    /**
     * Маппер из сущность в дто профиля
     */
    private Function<Profile, ProfileDto> mappingToDto() {
        return profile -> ProfileDto.builder()
                .uuid(profile.getUuid())
                .country(profile.getCountry())
                .money(profile.getMoney())
                .build();
    }
}
