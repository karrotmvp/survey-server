package com.daangn.survey.domain.survey.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Target {
    ALL(1, "모든 이웃"), BIZ_PROFILE_VISITED(2, "비즈 프로필 방문 이웃"), CUSTOMER(3, "단골")
    ;

    private int code;
    private String value;

    public static int findCode(String value){
        return Arrays.stream(Target.values()).filter(el -> el.getValue().equals(value)).findFirst().get().getCode();
    }

    public static String findValue(int code){
        return Arrays.stream(Target.values()).filter(el -> el.getCode() == code).findFirst().get().getValue();

    }
}
