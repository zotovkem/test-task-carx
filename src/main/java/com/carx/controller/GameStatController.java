package com.carx.controller;

import com.carx.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @author Created by ZotovES on 05.04.2020
 * Контроллер для приема игровой статистики пользователя
 */
@Controller
public class GameStatController {
    private final RedisRepository redisRepository;

    public GameStatController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
        receiveGameState();
    }

    private void receiveGameState() {
        post("/game-stat", (req, res) -> redisRepository.test());
    }
}
