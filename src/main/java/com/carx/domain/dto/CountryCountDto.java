package com.carx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 07.04.2020
 * Дто для аналитики зарегистрированных пользователей за период по странам
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryCountDto {
    /**
     * Страна
     */
    private String country;
    /**
     * Кол-во зарегестрированных
     */
    private Integer count;
}
