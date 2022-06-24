package com.github.asandersa.jrtb.command;

import com.github.asandersa.jrtb.javarushclient.JavaRushGroupClient;
import com.github.asandersa.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.asandersa.jrtb.javarushclient.dto.GroupRequestArgs;
import com.github.asandersa.jrtb.repository.entity.GroupSub;
import com.github.asandersa.jrtb.service.GroupSubService;
import com.github.asandersa.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.asandersa.jrtb.command.CommandName.ADD_GROUP_SUB;
import static com.github.asandersa.jrtb.command.CommandUtils.getChatId;
import static com.github.asandersa.jrtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if(getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split("\\s|@test_javarush_asandersa_bot ")[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if(isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub saveGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Я подписал тебя на группу " + saveGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());
        String message = """
                Чтобы подписаться на группу - передай команду вместе с ID группы.
                Например: /addGroupSub 16

                Я подготовил для тебя список всех групп - выбирай любую! :)
                
                Имя группы - ID группы
                %s""";
        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}
