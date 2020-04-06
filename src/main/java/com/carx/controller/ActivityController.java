package com.carx.controller;

import com.carx.domain.dto.ActivityDto;
import com.carx.service.impl.ActivityServiceImpl;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static spark.Spark.post;

/**
 * @author Created by ZotovES on 05.04.2020
 * Контроллер для приема игровой статистики пользователя
 */
@Controller
public class ActivityController {
    private final ActivityServiceImpl activityServiceImpl;
    private final Gson mapper;

    public ActivityController(ActivityServiceImpl activityServiceImpl, Gson mapper) {
        this.activityServiceImpl = activityServiceImpl;
        this.mapper = mapper;
        receiveGameState();
    }

    private void receiveGameState() {
        post("/game-stat", (req, res) -> {
            Optional.ofNullable(req.body())
                    .map(json -> mapper.fromJson(json, ActivityDto.class))
                    .ifPresent(activityServiceImpl::addActivity);
            return res.raw();
        });
    }
}
