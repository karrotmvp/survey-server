package com.daangn.survey.common.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseMessage {
    public static final String EXAMPLE = "메시지 예시입니다.";

    // Survey
    public static final String CREATE_SURVEY = "설문을 작성합니다.";
    public static final String READ_SURVEY_LIST = "설문 리스트를 조회합니다.";
    public static final String READ_SURVEY_DETAIL = "설문 디테일을 조회합니다.";
    public static final String DELETE_SURVEY = "설문을 삭제합니다.";


}