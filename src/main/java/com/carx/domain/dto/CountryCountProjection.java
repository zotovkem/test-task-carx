package com.carx.domain.dto;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Created by ZotovES on 08.04.2020
 */
public interface CountryCountProjection {
    /**
     * Страна
     */
    @Value(value = "#{target.country}")
    String getCountry();

    /**
     * Кол-во зарегестрированных
     */
    @Value(value = "#{target.count}")
    Integer getCount();
}
