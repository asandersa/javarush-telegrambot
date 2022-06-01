package com.github.asandersa.jrtb;

import com.github.asandersa.jrtb.command.Command;
import com.github.asandersa.jrtb.command.StopCommand;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.junit.jupiter.api.DisplayName;

import static com.github.asandersa.jrtb.command.CommandName.START;
import static com.github.asandersa.jrtb.command.CommandName.STOP;
import static com.github.asandersa.jrtb.command.StopCommand.STOP_MESSAGE;


@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }
}
