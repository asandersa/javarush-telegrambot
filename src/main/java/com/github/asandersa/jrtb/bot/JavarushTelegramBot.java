package com.github.asandersa.jrtb.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}") //аннотация SpringBoot, подтягивает значения из application.properties
    private String username;

    @Value("${bot.token}")
    private String token;

    /*
     В getBotUsername() приходит имя бота, к которому будем соединяться.
     */
    @Override
    public String getBotUsername() {
        return username;
    }

    /*
     В getBotToken() приходит токен бота, к которому будем соединяться.
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /*
    Сообщения от пользователей поступают в onUpdateReceived(Update update)
     */
    @Override
    public void onUpdateReceived(Update update) {
        /*
        Проверяем существование сообщения, извлекаем текст и айдишник.
         */
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            /*
            Создаем объект для отправки сообщения, передаем в него текст и айди.
             */
            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText(message);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                //печать в логги
                e.printStackTrace();
            }


        }


    }
}
