package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.asandersa.jrtb.command.CommandName.*;

/**
 * Help {@link Command}.
 */

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("""
                    ✨<b>Дотупные команды</b>✨

                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу со мной
                    %s - приостановить работу со мной
                    
                    Работа с подписками на группы:
                    %s - подписаться на группу статей
                    %s - получить список групп, на которые подписан
                    
                    %s - получить помощь в работе со мной
                    %s - получить статистику бота
                    """,
            START.getCommandName(),
            STOP.getCommandName(),
            ADD_GROUP_SUB.getCommandName(),
            LIST_GROUP_SUB.getCommandName(),
            HELP.getCommandName(),
            STAT.getCommandName());



    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);

    }
}
