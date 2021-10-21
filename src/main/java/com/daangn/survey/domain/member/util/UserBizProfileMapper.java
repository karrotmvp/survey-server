package com.daangn.survey.domain.member.util;

public class UserBizProfileMapper {
    // 유저 id를 받고,
    // 추후에 비즈프로필 정보를 가져왔을 때,
    // 엔티티 매핑해서 저장하는 게 전부이지 않을까?
    // 단순히 비즈프로필 저장 서비스로 가져가면 되는 걸까???
    // 그렇다면 해당 서비스를 호출하는 시점은 언제가 될까?
    // 유저가 로그인을 한다. -> 설문 작성하기를 누른다 -> 비즈프로필 프리셋을 통해 비즈프로필을 선택한다
    // -> 비즈프로필 정보를 가져온다(karrotApiUtil.resolveBusinessProfile()) -> 이미 있는 정보인지 본다.
    // -> 있으면 업데이트만 한다. -> 없으면 새로 만드는데, 이때 유저 정보를 넣으면 된다.
}
