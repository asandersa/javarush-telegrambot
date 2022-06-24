package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.dto.GroupStatDTO;
import com.github.asandersa.jrtb.dto.StatisticDTO;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.asandersa.jrtb.command.AbstractCommandTest.prepareUpdate;
import static com.github.asandersa.jrtb.command.CommandName.STAT;
import static com.github.asandersa.jrtb.command.StatCommand.STAT_MESSAGE;
import static java.lang.String.format;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {

    private SendBotMessageService sendBotMessageService;
    private StatisticsService statisticsService;
    private Command statCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        statisticsService = Mockito.mock(StatisticsService.class);
        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public  void shouldProperlySendMessage() {
        //given
        Long chatId = 1234567L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "group", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupStatDTO), 2.5);
        Mockito.when(statisticsService.countBotStatistic())
                .thenReturn(statisticDTO);

        //when
        statCommand.execute(prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                format("%s (id = %s) - %s подписчиков", groupStatDTO.getTitle(), groupStatDTO.getId(), groupStatDTO.getActiveUserCount())));
    }
}
