package com.carx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Created by ZotovES on 05.04.2020
 * Дто активности игрока
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ActivityDto {
    private UUID uuid;

    /**
     * Дата активности
     */
    private ZonedDateTime activityDate;

    /**
     * Значение
     */
    private Integer activity;
}

