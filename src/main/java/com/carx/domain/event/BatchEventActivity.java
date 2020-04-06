package com.carx.domain.event;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author Created by ZotovES on 05.04.2020
 * Событие с пакето полученных активностей пользователей
 */
@AllArgsConstructor
public class BatchEventActivity {
    private Object object;
    private List<String> activity;
}
