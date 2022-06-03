package com.github.asandersa.jrtb.repository.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity //сущность для работы с БД
@Table(name = "tg_user") //определяем имя таблицы
public class TelegramUser {

    @Id //определяем Primary Key
    @Column(name = "chat_id") //определяем имя атрибута
    private String chatId;

    @Column(name = "active")
    private boolean active;

}
