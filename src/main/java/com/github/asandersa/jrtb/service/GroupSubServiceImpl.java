package com.github.asandersa.jrtb.service;

import com.github.asandersa.jrtb.javarushclient.JavaRushGroupClient;
import com.github.asandersa.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.asandersa.jrtb.repository.GroupSubRepository;
import com.github.asandersa.jrtb.repository.entity.GroupSub;
import com.github.asandersa.jrtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;
    private final JavaRushGroupClient javaRushGroupClient;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService, JavaRushGroupClient javaRushGroupClient) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
        this.javaRushGroupClient = javaRushGroupClient;
    }

    @Override
    public GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromBD = groupSubRepository.findById(groupDiscussionInfo.getId());
        if(groupSubFromBD.isPresent()) {
            groupSub = groupSubFromBD.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setLastArticleId(javaRushGroupClient.findLastPostId(groupDiscussionInfo.getId()));
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public GroupSub save(GroupSub groupSub) {
        return groupSubRepository.save(groupSub);
    }

    @Override
    public Optional<GroupSub> findById(Integer id) {
        return groupSubRepository.findById(id);
    }

    @Override
    public List<GroupSub> findAll() {
        return groupSubRepository.findAll();
    }
}
