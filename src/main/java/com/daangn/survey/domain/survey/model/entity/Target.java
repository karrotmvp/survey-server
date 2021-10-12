package com.daangn.survey.domain.survey.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Target {
    ALL(0, "모든 이웃"), CUSTOMER(1, "단골 이웃"), BIZ_PROFILE_VISITED(2, "비즈 프로필을 방문한 이웃")
    ;

    private int code;
    private String korean;

    public static int findValue(String target){
        return Arrays.stream(Target.values()).filter(el -> el.equals(target)).findFirst().get().getCode();
    }
}
