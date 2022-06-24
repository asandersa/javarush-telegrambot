package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.javarushclient.JavaRushGroupClient;
import com.github.asandersa.jrtb.repository.entity.GroupSub;
import com.github.asandersa.jrtb.repository.entity.TelegramUser;
import com.github.asandersa.jrtb.service.GroupSubService;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import com.github.asandersa.jrtb.service.TelegramUserService;
import org.hibernate.mapping.Collection;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.asandersa.jrtb.command.CommandName.DELETE_GROUP_SUB;
import static com.github.asandersa.jrtb.command.CommandUtils.getChatId;
import static com.github.asandersa.jrtb.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNumeric;


/**
 * Delete Group subscription {@link Command}.
 */
public class DeleteGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if(getMessage(update).equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split("\\s|@test_javarush_asandersa_bot ")[1];
        Long chatId = getChatId(update);
        if(isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if(optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, format("Я удалил подписку на группу: %s", groupSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, "Группа не найдена");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "Ошибка в формату ID группы \n " + "ID группы должен быть целым положительным числом");
        }
    }

    public void sendGroupIdList(Long chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "У тебя пока нет подписок на группы. Чтобы добавить подписку введи /addGroupSub";
        } else {

            String userGroupSubData = groupSubs.stream()
                    .map(group -> format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());

            message = String.format("""
                    Чтобы удалить подписку на группу - передай команду вместе с ID группы.
                    Например: /deleteGroupSub 16

                    Вот список всех групп, на которые ты подписан:
                    %s""", userGroupSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
