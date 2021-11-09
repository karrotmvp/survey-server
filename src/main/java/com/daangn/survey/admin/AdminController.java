package com.daangn.survey.admin;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.service.AdminService;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.service.MemberService;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.service.ResponseService;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
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
    private final ResponseService responseService;
    private final AdminService adminService;

    private final MemberMapper memberMapper;

    @GetMapping
    public String getSurveys(Model model, @RequestParam(required = false) String filter){
        List<SurveySummaryDto> surveys = filter != null && filter.equalsIgnoreCase("all")
                                        ? adminService.findAll()
                                        : adminService.findSurveysAboutPublished();

        model.addAttribute("surveys", surveys);
        return "admin/surveys";
    }

    @GetMapping("/biz-profiles")
    public String bizProfiles(Model model, @RequestParam(required = false) String filter){

        List<AdminMemberDto> memberDtoList = filter != null && filter.equalsIgnoreCase("counting")
                ? adminService.getMembersByCondition()
                .stream()
                .map(memberMapper::toAdminMemberDto)
                .collect(Collectors.toList())
                : adminService.getAllBizProfiles()
                                .stream()
                                .map(memberMapper::toAdminMemberDto)
                                .collect(Collectors.toList());

        model.addAttribute("members", memberDtoList);
        return "admin/biz-profiles";
    }

    @GetMapping("/responses/surveys/{surveyId}")
    public String surveyResponses(@PathVariable Long surveyId, Model model){
        SurveyDto survey = surveyService.findBySurveyId(surveyId);

        model.addAttribute("survey", survey);
        model.addAttribute("responses", adminService.getAdminResponses(surveyId));

        return "admin/responses";
    }

    @GetMapping("/responses/{responseId}")
    public String getResponseDetail(@PathVariable Long responseId, Model model){
        SurveyResponse surveyResponse = responseService.getSurveyResponse(responseId);

        model.addAttribute("survey", surveyResponse.getSurvey());
        model.addAttribute("responses", adminService.getAdminResponseDetail(surveyResponse));

        return "admin/response-detail";
    }

    @GetMapping("/members/{daangnId}")
    public String memberSurveys(@PathVariable String daangnId, Model model){
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
