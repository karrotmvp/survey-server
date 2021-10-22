package com.daangn.survey.admin;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.service.MemberService;
import com.daangn.survey.domain.survey.service.SurveyService;
import com.daangn.survey.domain.survey.service.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/daangn/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final SurveyServiceImpl surveyService;
    private final MemberMapper memberMapper;

    @GetMapping
    public String adminIndex(Model model){

        List<AdminMemberDto> memberDtoList =  memberService.getAllMembers().stream().map(memberMapper::toAdminMemberDto).collect(Collectors.toList());

        model.addAttribute("members", memberDtoList);
        return "admin/main";
    }

    @GetMapping("/members/{daangnId}")
    public String memberSurveyList(@PathVariable String daangnId, Model model){
        Member member = memberService.findByDaangnId(daangnId);
        model.addAttribute("surveys", surveyService.findSurveysByMemberId(member.getId()));
        return "admin/surveys";
    }

    @GetMapping("/surveys/{surveyId}")
    public String surveyDetail(@PathVariable Long surveyId, Model model){
        model.addAttribute("survey", surveyService.findBySurveyId(surveyId));
        return "admin/survey-detail";
    }
}
