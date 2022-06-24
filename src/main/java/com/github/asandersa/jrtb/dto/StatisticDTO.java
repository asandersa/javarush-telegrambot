package com.github.asandersa.jrtb.dto;

import com.github.asandersa.jrtb.dto.GroupStatDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * DTO for getting bot statistic.
 */
@Data
@EqualsAndHashCode
public class StatisticDTO {
    private final int activeUserCount;
    private final int inactiveUserCount;
    private final List<GroupStatDTO> groupStatDTOs;
    private final double averageGroupCountByUser;
}
