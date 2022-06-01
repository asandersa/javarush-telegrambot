package com.github.asandersa.jrtb.service;

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
    void sendMessage(String chatId, String message);

}
