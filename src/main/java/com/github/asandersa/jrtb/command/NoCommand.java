package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * No {@link Command}.
 */

public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша (/).\n"
                                + "Чтобы посмотреть список моих команд введи /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), NO_MESSAGE);

    }
}
