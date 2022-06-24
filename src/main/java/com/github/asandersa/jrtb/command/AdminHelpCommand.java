package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.asandersa.jrtb.command.CommandName.STAT;
import static java.lang.String.format;

/**
 * Admin Help {@link Command}
 */
public class AdminHelpCommand implements Command {
    public static final String ADMIN_HELP_MESSAGE = format("""
            Доступные команды для администратора:

            Получить статистику
            %s - статистика бота
            """, STAT.getCommandName()
    );

    private final SendBotMessageService sendBotMessageService;

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), ADMIN_HELP_MESSAGE);

    }
}
