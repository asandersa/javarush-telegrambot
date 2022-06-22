package com.github.asandersa.jrtb.service;

import java.util.List;

/**
 * Сервис для отправки сообщений через telegram-bot.
 */
public interface SendBotMessageService {
    /**
     * Send message via telegram bot.
     *
     * @param chatId provided chatId in which messages would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(Long chatId, String message);

    /**
     * Send messages via telegram bot.
     *
     * @param chatId  provided chatId in which would be sent.
     * @param message collection of provided messages to be sent.
     */
    void sendMessage(Long chatId, List<String> message);
}

