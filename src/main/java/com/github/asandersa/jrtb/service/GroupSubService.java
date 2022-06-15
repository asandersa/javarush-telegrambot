package com.github.asandersa.jrtb.service;

import com.github.asandersa.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.asandersa.jrtb.repository.entity.GroupSub;

import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer id);
}
