package com.github.asandersa.jrtb.command;


import com.github.asandersa.jrtb.command.Command;
import com.github.asandersa.jrtb.command.CommandContainer;
import com.github.asandersa.jrtb.command.CommandName;
import com.github.asandersa.jrtb.command.UnknownCommand;
import com.github.asandersa.jrtb.javarushclient.JavaRushGroupClient;
import com.github.asandersa.jrtb.service.GroupSubService;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.event.ItemListener;
import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, groupClient, groupSubService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
                Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }


    @Test
    public void shouldReturnUnknownCommand () {
        //given
        String unknownCommand = "/hfdjsk";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());

    }


}
