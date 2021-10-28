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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
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
    public String adminIndex(Model model, @RequestParam(required = false) String filter){
        List<AdminMemberDto> memberDtoList = new LinkedList<>();
        if(filter == null)
             memberDtoList = memberService.getAllMembers().stream().map(memberMapper::toAdminMemberDto).collect(Collectors.toList());
        else if(filter.equalsIgnoreCase("counting")){
            memberDtoList = memberService.getMembersByCondition().stream().map(memberMapper::toAdminMemberDto).collect(Collectors.toList());
        }

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

    @GetMapping("/redirect")
    public String redirect(){
        return "redirect:karrot.alpha://minikarrot/router?navbar=false&scrollable=false&app=https%3A%2F%2Fwebview.alpha.kr.karrotmarket.com%2Fmini-redirect&path=%2FN4IghgDhCSAmIC4S1gBgEboJxbAZgFYiAWWPMLARgFNLjrZ0AOPJ1JlgJhABoQAnBgEtBAYwAuiEAAtx4iAGcEAemWwAXnQBsAW1EB2AgDshldQDpRAGwD2AV1gAzfjaPjzR6pIC%2BQA";
    }

}
