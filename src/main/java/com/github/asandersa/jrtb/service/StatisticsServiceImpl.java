package com.github.asandersa.jrtb.service;

import com.github.asandersa.jrtb.dto.GroupStatDTO;
import com.github.asandersa.jrtb.dto.StatisticDTO;
import com.github.asandersa.jrtb.repository.entity.TelegramUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final GroupSubService groupSubService;
    private final TelegramUserService telegramUserService;

    public StatisticsServiceImpl(GroupSubService groupSubService, TelegramUserService telegramUserService) {
        this.groupSubService = groupSubService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public StatisticDTO countBotStatistic() {
        List<GroupStatDTO> groupStatDTO = groupSubService.findAll().stream()
                .filter(it -> !isEmpty(it.getUsers()))
                .map(groupSub -> new GroupStatDTO(groupSub.getId(), groupSub.getTitle(), groupSub.getUsers().size()))
                .collect(Collectors.toList());
        List<TelegramUser> activeUsers = telegramUserService.findAllActiveUsers();
        List<TelegramUser> inactiveUsers = telegramUserService.findAllInActiveUsers();

        double groupPerUser = getGroupPerUser(activeUsers);
        return new StatisticDTO(activeUsers.size(), inactiveUsers.size(), groupStatDTO, groupPerUser);
    }

    private double getGroupPerUser(List<TelegramUser> activeUsers) {
        return (double) activeUsers.stream().mapToInt(it -> it.getGroupSubs().size()).sum() / activeUsers.size();
    }
}
