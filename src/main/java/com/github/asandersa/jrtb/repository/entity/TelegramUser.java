package com.github.asandersa.jrtb.repository.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity //сущность для работы с БД
@Table(name = "tg_user") //определяем имя таблицы
@EqualsAndHashCode(exclude = "groupSubs")
public class TelegramUser {

    @Id //определяем Primary Key
    @Column(name = "chat_id") //определяем имя атрибута
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<GroupSub> groupSubs;

}
