package com.daangn.survey.common.message;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseMessage {
    public static final String EXAMPLE = "메시지 예시입니다.";

    // Survey
    public static final String CREATE_SURVEY = "설문을 작성합니다.";
    public static final String READ_SURVEY_LIST = "설문 리스트를 조회합니다.";
    public static final String READ_SURVEY_DETAIL = "설문 상세 정보를 조회합니다.";
    public static final String READ_SURVEY_BRIEF = "설문 요약 정보를 조회합니다.";
    public static final String DELETE_SURVEY = "설문을 삭제합니다.";
    public static final String PUBLISH_SURVEY = "설문을 배포합니다.";

    // Auth
    public static final String CREATE_JWT_CUSTOMER = "JWT_CUSTOMER를 생성합니다.";
    public static final String CREATE_JWT_BUSINESS = "JWT_BUSINESS를 생성합니다.";
    public static final String SAVE_USER_PROFILE = "유저 프로필을 저장합니다.";

    // Response
    public static final String CREATE_RESPONSE = "답변을 작성합니다.";
    public static final String READ_USER_SURVEY_RESPONDED = "해당 유저가 이전에 답변을 했는 지 조회합니다.";

    // Member
    public static final String READ_PROFILE = "프로필을 조회합니다.";

    // Feedback
    public static final String CREATE_FEEDBACK = "피드백을 작성합니다.";

    // Notification
    public static final String CREATE_NOTIFICATION = "알림을 신청합니다.";

    // Aggregation
    public static final String READ_AGGREGATION = "설문 집계 결과를 조회합니다.";
    public static final String READ_RESPONSES_BRIEF = "설문 응답 요약 정보를 조회합니다.";

    // ShortUrl
    public static final String READ_SHORT_URL = "단축 URL을 읽습니다.";

}