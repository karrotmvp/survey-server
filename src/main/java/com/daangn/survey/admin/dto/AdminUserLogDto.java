package com.daangn.survey.admin.dto;

import com.daangn.survey.core.log.model.ShortUrlLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class AdminUserLogDto {
    private List<ShortUrlLog> shortUrlLogs;
    private Long count;
}
