package com.carx.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Created by ZotovES on 05.04.2020
 * Активности пользователя
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "activity", schema = "carx")
public class Activity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор профиля пользователя
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_uuid", referencedColumnName = "uuid")
    private Profile profile;

    /**
     * Дата активности
     */
    @Column(name = "activity_date")
    private ZonedDateTime activityDate;

    /**
     * Значение
     */
    @Column(name = "activity")
    private Integer activity;
}

