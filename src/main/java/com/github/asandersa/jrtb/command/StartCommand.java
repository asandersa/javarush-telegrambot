package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    public final static String START_MESSAGE = """
            Привет!

            Я Javarush Telegram Bot, разработанный @asandersa \uD83D\uDE07\s

            Я помогу тебе быть вкурсе тех статей, что тебе интересны.
            Я еще маленький и только учусь, не будь строг ко мне!\uD83E\uDD0D\uD83E\uDD0D\uD83E\uDD0D""";


    // Здесь не добавляем сервис через получение из Application Context.
    // Потому что если это сделать так, то будет циклическая зависимость, которая
    // ломает работу приложения.
    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);

    }
}
