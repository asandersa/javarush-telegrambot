package com.github.asandersa.jrtb;

import com.github.asandersa.jrtb.command.Command;
import com.github.asandersa.jrtb.command.HelpCommand;
import org.junit.jupiter.api.DisplayName;

import static com.github.asandersa.jrtb.command.CommandName.HELP;
import static com.github.asandersa.jrtb.command.HelpCommand.HELP_MESSAGE;


@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
