package com.github.asandersa.jrtb;

import com.github.asandersa.jrtb.command.Command;
import com.github.asandersa.jrtb.command.StatCommand;

import static com.github.asandersa.jrtb.command.CommandName.STAT;
import static com.github.asandersa.jrtb.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}