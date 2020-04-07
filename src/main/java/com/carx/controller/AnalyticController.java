package com.carx.controller;

import com.carx.domain.dto.ActivityDto;
import com.carx.domain.dto.CountryCountDto;
import com.carx.domain.dto.ProfileDto;
import com.carx.domain.entity.Activity;
import com.carx.domain.entity.Profile;
import com.carx.service.ActivityService;
import com.carx.service.ProfileService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import spark.QueryParamsMap;
import spark.Request;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static spark.Spark.*;

/**
 * @author Created by ZotovES on 07.04.2020
 * Контроллер для получения аналитический запросов
 */
@Controller
public class AnalyticController {
    private final ProfileService profileService;
    private final ActivityService activityService;
    private final Gson mapper;

    public AnalyticController(ProfileService profileService, ActivityService activityService, Gson mapper) {
        this.profileService = profileService;
        this.activityService = activityService;
        this.mapper = mapper;

        after((req, res) -> {
            res.type("application/json");
        });
        exception(IllegalArgumentException.class, (exception, req, res) ->
                res.body(mapper.toJson(exception.getMessage())));

        getUserCreateBetweenDate();
        getProfileActivity();
        getProfileByUuid();
    }

    /**
     * Получить пользователей зарегистрированных за период
     */
    private void getUserCreateBetweenDate() {
        get("/analytic/new-user", (req, res) -> {
            var beginDate = getBeginDate(req);
            var endDate = getEndDate(req);
            validateBetween(beginDate, endDate);

            return profileService.countProfilesBetweenCreateDate(beginDate, endDate).stream()
                    .map(p -> new CountryCountDto(p.getCountry(), p.getCount()))
                    .map(p -> mapper.toJson(p, CountryCountDto.class))
                    .collect(toList());
        });
    }

    /**
     * Получить профиль пользователя по уникальному идентификатору пользователя
     */
    private void getProfileByUuid() {
        get("/analytic/rich-profile", (req, res) -> ofNullable(req.queryMap("limit"))
                .map(QueryParamsMap::integerValue)
                .map(profileService::limitProfilesRich)
                .map(Collection::stream).get()
                .map(mappingProfileToDto())
                .map(dto -> mapper.toJson(dto, ProfileDto.class))
                .collect(toList()));
    }

    /**
     * Получить список активностей для пользователя за период
     */
    private void getProfileActivity() {
        get("/analytic/profile-activity", (req, res) -> {
            var beginDate = getBeginDate(req);
            var endDate = getEndDate(req);
            validateBetween(beginDate, endDate);
            var uuid = ofNullable(req.queryMap("uuid"))
                    .map(QueryParamsMap::value)
                    .map(parseStringToUuidOrNull())
                    .orElseThrow(() -> new IllegalArgumentException("Не верный идентификатор пользователя"));

            return activityService.findActivityByUuidAndActivityDateBetween(uuid, beginDate, endDate).stream()
                    .map(mappingActivityToDto())
                    .map(dto -> mapper.toJson(dto, ActivityDto.class))
                    .collect(toList());
        });
    }

    /**
     * Проверяет диапазон дат
     *
     * @param beginDate начальная дата диапазона
     * @param endDate   конечная дата диапазона
     */
    private void validateBetween(ZonedDateTime beginDate, ZonedDateTime endDate) {
        if (beginDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Начальная дата меньше конечной");
        }
    }

    /**
     * Получить из параметров конечную дату диапазона
     *
     * @param req запрос
     * @return конечная дата
     */
    private ZonedDateTime getEndDate(Request req) {
        return ofNullable(req.queryMap("endDate"))
                .map(QueryParamsMap::value)
                .map(parseStringToDateOrNull())
                .map(date -> date.plusDays(1).minusSeconds(1))
                .orElseThrow(() -> new IllegalArgumentException("Задайте конечную дату диапазона"));
    }

    /**
     * Получить из параметров начальную дату диапазона
     *
     * @param req запрос
     * @return начальная дата
     */
    private ZonedDateTime getBeginDate(Request req) {
        return ofNullable(req.queryMap("beginDate"))
                .map(QueryParamsMap::value)
                .map(parseStringToDateOrNull())
                .orElseThrow(() -> new IllegalArgumentException("Задайте начальную дату диапазона"));
    }

    /**
     * Маппер из сущность в дто активности
     */
    private Function<Activity, ActivityDto> mappingActivityToDto() {
        return activity -> ActivityDto.builder()
                .uuid(activity.getProfile().getUuid())
                .activity(activity.getActivity())
                .activityDate(activity.getActivityDate())
                .build();
    }

    /**
     * Маппер из сущность в дто профиля
     */
    private Function<Profile, ProfileDto> mappingProfileToDto() {
        return profile -> ProfileDto.builder()
                .uuid(profile.getUuid())
                .country(profile.getCountry())
                .money(profile.getMoney())
                .build();
    }

    /**
     * Парсит строку в uuid  или возвращает null
     */
    private Function<String, ZonedDateTime> parseStringToDateOrNull() {
        return str -> {
            try {
                return LocalDate.parse(str).atStartOfDay(ZoneId.systemDefault());
            } catch (DateTimeParseException ex) {
                return null;
            }
        };
    }

    /**
     * Парсит строку в uuid  или возвращает null
     */
    private Function<String, UUID> parseStringToUuidOrNull() {
        return str -> {
            try {
                return UUID.fromString(str);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        };
    }
}
