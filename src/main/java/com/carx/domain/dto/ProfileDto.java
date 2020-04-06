package com.carx.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

/**
 * @author Created by ZotovES on 05.04.2020
 * Dto профиля игрока
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProfileDto {
    /**
     * Идентификатор профиля пользователя
     */
    private UUID uuid;

    /**
     * Денежный счет пользователя
     */
    @Column(name = "money")
    private Integer money;

    /**
     * Страна
     */
    @Column(name = "country")
    private String country;

}

