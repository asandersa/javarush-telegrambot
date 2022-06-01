package com.github.asandersa.jrtb.bot;

import com.github.asandersa.jrtb.command.CommandContainer;
import com.github.asandersa.jrtb.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.asandersa.jrtb.command.CommandName.NO;


@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandConteiner;

    @Value("${bot.username}") //аннотация SpringBoot, подтягивает значения из application.properties
    private String username;
    @Value("${bot.token}")
    private String token;

    public JavarushTelegramBot() {
        this.commandConteiner = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

    /**
     В getBotUsername() приходит имя бота, к которому будем соединяться.
     */
    @Override
    public String getBotUsername() {
        return username;
    }

    /**
     В getBotToken() приходит токен бота, к которому будем соединяться.
     */
    @Override
    public String getBotToken() {
        return token;
    }

    /**
    Сообщения от пользователей поступают в onUpdateReceived(Update update)
     */
    @Override
    public void onUpdateReceived(Update update) {
        //Проверяем существование сообщения, извлекаем текст и айдишник.
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandConteiner.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandConteiner.retrieveCommand(NO.getCommandName()).execute(update);
            }

        }


    }
}
