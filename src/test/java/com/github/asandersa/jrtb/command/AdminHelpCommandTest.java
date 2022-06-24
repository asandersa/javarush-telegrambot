package com.github.asandersa.jrtb.command;

import static com.github.asandersa.jrtb.command.AdminHelpCommand.ADMIN_HELP_MESSAGE;
import static com.github.asandersa.jrtb.command.CommandName.ADMIN_HELP;

public class AdminHelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return ADMIN_HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADMIN_HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendBotMessageService);
    }
}
