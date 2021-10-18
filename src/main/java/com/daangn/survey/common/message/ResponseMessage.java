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

    // Auth
    public static final String CREATE_JWT_CUSTOMER = "JWT_CUSTOMER를 생성합니다.";
    public static final String CREATE_JWT_BUSINESS = "JWT_BUSINESS를 생성합니다.";

    // Response
    public static final String CREATE_RESPONSE = "답변을 작성합니다.";

    // Member
    public static final String READ_PROFILE = "프로필을 조회합니다.";

    // Feedback
    public static final String CREATE_FEEDBACK = "피드백을 작성합니다.";

}