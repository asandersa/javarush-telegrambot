package com.github.asandersa.jrtb.command;


import com.github.asandersa.jrtb.javarushclient.JavaRushGroupClient;
import com.github.asandersa.jrtb.service.GroupSubService;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.StatisticsService;
import com.github.asandersa.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, groupClient, groupSubService, statisticsService, Collections.singletonList("username"));
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
                Command command = commandContainer.retrieveCommand(commandName.getCommandName(), "username");
                Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }


    @Test
    public void shouldReturnUnknownCommand () {
        //given
        String unknownCommand = "/hfdjsk";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand, "username");

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());

    }


}
