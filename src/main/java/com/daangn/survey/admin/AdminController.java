package com.daangn.survey.admin;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.admin.service.AdminService;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.domain.aggregation.service.AggregationService;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import com.daangn.survey.domain.response.service.ResponseService;
import com.daangn.survey.domain.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.daangn.survey.common.message.ResponseMessage.PUBLISH_SURVEY;

@Controller
@RequestMapping("/daangn/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SurveyService surveyService;
    private final ResponseService responseService;
    private final AdminService adminService;
    private final AggregationService aggregationService;

    /**
     * Surveys
     */
    @GetMapping
    public String getSurveys(Model model, @RequestParam(required = false) String filter){
        List<AdminSurveyDto> surveys = filter != null && filter.equalsIgnoreCase("all")
                                        ? adminService.getAdminSurveysWhere(null)
                                        : adminService.getSurveysAboutPublished();

        model.addAttribute("surveys", surveys);
        return "admin/surveys";
    }

    @GetMapping("/members/{memberId}")
    public String memberSurveys(@PathVariable Long memberId, Model model){
        model.addAttribute("surveys", adminService.getAdminSurveysWhere(memberId));
        return "admin/surveys";
    }

    @GetMapping("/surveys/{surveyId}")
    public String surveyDetail(@PathVariable Long surveyId, Model model){
        model.addAttribute("survey", surveyService.findBySurveyId(surveyId));
        return "admin/survey-detail";
    }

    /**
     * Members
     */
    @GetMapping("/biz-profiles")
    public String bizProfiles(Model model, @RequestParam(required = false) String filter){

        List<AdminMemberDto> memberDtoList = filter != null && filter.equalsIgnoreCase("counting")
                ? adminService.getMembersWhere(0, "ROLE_BIZ")
                : adminService.getMembersWhere(null, "ROLE_BIZ");

        model.addAttribute("members", memberDtoList);
        return "admin/biz-profiles";
    }

    /**
     * Responses
     */
    @GetMapping("/responses/surveys/{surveyId}")
    public String surveyResponses(@PathVariable Long surveyId, Model model){

        model.addAttribute("responses", adminService.getAdminResponsesWhere(surveyId));
        return "admin/responses";
    }

    @GetMapping("/responses")
    public String getAllSurveyResponses(Model model){

        model.addAttribute("responses", adminService.getAdminResponsesWhere(null));
        return "admin/responses";
    }

    @GetMapping("/responses/{responseId}")
    public String getResponseDetail(@PathVariable Long responseId, Model model){
        SurveyResponse surveyResponse = responseService.getSurveyResponse(responseId);
        model.addAttribute("survey", surveyService.findBySurveyId(surveyResponse.getSurvey().getId()));
        model.addAttribute("responses", aggregationService.getIndividualSurveyResponse(surveyResponse));

        return "admin/response-detail";
    }

    @ResponseBody
    @PatchMapping("/surveys/{surveyId}/publish")
    public ResponseEntity<ResponseDto<?>> patchSurveyToPublished(@PathVariable Long surveyId){
        surveyService.patchSurveyAboutPublishing(surveyId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, PUBLISH_SURVEY));
    }

}
