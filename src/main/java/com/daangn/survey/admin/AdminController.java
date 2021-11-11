package com.daangn.survey.admin;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.admin.service.AdminService;
import com.daangn.survey.domain.aggregation.service.AggregationService;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.service.MemberService;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.service.ResponseService;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.service.SurveyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/daangn/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SurveyServiceImpl surveyService;
    private final ResponseService responseService;
    private final AdminService adminService;
    private final AggregationService aggregationService;


    @GetMapping
    public String getSurveys(Model model, @RequestParam(required = false) String filter){
        List<AdminSurveyDto> surveys = filter != null && filter.equalsIgnoreCase("all")
                                        ? adminService.getAdminSurveyDtos()
                                        : adminService.getSurveysAboutPublished();

        model.addAttribute("surveys", surveys);
        return "admin/surveys";
    }

    @GetMapping("/biz-profiles")
    public String bizProfiles(Model model, @RequestParam(required = false) String filter){

        List<AdminMemberDto> memberDtoList = filter != null && filter.equalsIgnoreCase("counting")
                ? adminService.getMembersByCondition()
                : adminService.getAllBizProfiles();

        model.addAttribute("members", memberDtoList);
        return "admin/biz-profiles";
    }

    @GetMapping("/responses/surveys/{surveyId}")
    public String surveyResponses(@PathVariable Long surveyId, Model model){

        model.addAttribute("responses", adminService.getAdminResponses(surveyId));
        return "admin/responses";
    }

    @GetMapping("/responses")
    public String getAllSurveyResponses(Model model){

        model.addAttribute("responses", adminService.getAllAdminResponses());
        return "admin/responses";
    }

    @GetMapping("/responses/{responseId}")
    public String getResponseDetail(@PathVariable Long responseId, Model model){
        SurveyResponse surveyResponse = responseService.getSurveyResponse(responseId);
        model.addAttribute("survey", surveyService.findBySurveyId(surveyResponse.getSurvey().getId()));
        model.addAttribute("responses", aggregationService.getIndividualSurveyResponse(surveyResponse));

        return "admin/response-detail";
    }

    @GetMapping("/members/{memberId}")
    public String memberSurveys(@PathVariable Long memberId, Model model){
        model.addAttribute("surveys", adminService.getAdminSurveysByMemberId(memberId));
        return "admin/surveys";
    }

    @GetMapping("/surveys/{surveyId}")
    public String surveyDetail(@PathVariable Long surveyId, Model model){
        model.addAttribute("survey", surveyService.findBySurveyId(surveyId));
        return "admin/survey-detail";
    }

}
