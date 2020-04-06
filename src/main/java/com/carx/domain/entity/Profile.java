package com.carx.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Created by ZotovES on 05.04.2020
 * Профиль игрока
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "profile", schema = "carx")
public class Profile {
    /**
     * Идентификатор профиля пользователя
     */
    @Id
    @Column(name = "uuid")
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

    /**
     * Дата создания профиля
     */
    @Column(name = "create_date")
    private ZonedDateTime createDate;
}

