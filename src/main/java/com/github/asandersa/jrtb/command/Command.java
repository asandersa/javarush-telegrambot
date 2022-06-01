package com.github.asandersa.jrtb.command;

import org.telegram.telegrambots.meta.api.objects.Update;


/**
Реализуем интерфейс паттерна Команда, общий для всех команд onUpdateReceived().
Command interface for handling telegram-bot commands.
 */
public interface Command {

    /**
     * Main method, which is executing command logic.
     *
     * @param update provided {@link Update} object with all the needed data for command.
     */

    void execute(Update update);
}
