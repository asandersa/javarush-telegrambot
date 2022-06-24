package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.dto.StatisticDTO;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.StatisticsService;
import com.github.asandersa.jrtb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

/**
 * Statistics {@link Command}
 */
@AdminCommand
public class StatCommand implements Command {

    private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;

    public final static String STAT_MESSAGE = """
            <b>Моя статистика</b>:
            количество активных пользователей: %s
            количество неактивных пользователей: %s
            среднее количество групп на пользователя: %s
            
            <b>Информация по активным группам</b>:
            %s""";

    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
        this.sendBotMessageService = sendBotMessageService;
    }


    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroup = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId(), String.format( STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroup));

    }
}
