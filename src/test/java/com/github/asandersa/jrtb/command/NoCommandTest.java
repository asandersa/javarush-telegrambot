package com.github.asandersa.jrtb.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.asandersa.jrtb.command.CommandName.NO;
import static com.github.asandersa.jrtb.command.NoCommand.NO_MESSAGE;


@DisplayName("Unit-level testing for NoCommandTest")
public class NoCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }
}
