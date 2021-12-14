package com.daangn.survey.admin;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.admin.service.AdminService;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.domain.deprecated.aggregation.service.AggregationService;
import com.daangn.survey.domain.deprecated.response.service.ResponseService;
import com.daangn.survey.domain.deprecated.survey.survey.service.SurveyService;
import com.daangn.survey.mongo.MongoService;
import com.daangn.survey.mongo.aggregate.individual.IndividualQuestionMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.daangn.survey.common.message.ResponseMessage.PUBLISH_SURVEY;

@Controller
@RequestMapping("/daangn/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SurveyService surveyService;
    private final AdminService adminService;
    private final MongoService mongoService;

    /**
     * Surveys
     */
    @GetMapping
    public String getSurveys(
            Model model,
            @RequestParam(required = false, defaultValue = "") String filter
    ){
        List<AdminSurveyDto> surveys = adminService.getMongoSurveys();

        switch (filter){
            case "answered":
                surveys = surveys.stream().filter(survey -> survey.getResponseCount() > 0).collect(Collectors.toList());
            default:
                model.addAttribute("surveys", surveys);
        }

        return "admin/surveys";
    }

    @GetMapping("/members/{memberId}")
    public String memberSurveys(@PathVariable Long memberId, Model model){
        model.addAttribute("surveys", adminService.getAdminSurveysWhere(memberId));
        return "admin/surveys";
    }

    @GetMapping("/surveys/{surveyId}")
    public String surveyDetail(@PathVariable Long surveyId, Model model){
        model.addAttribute("survey", mongoService.findSurvey(surveyId));
        return "admin/survey-detail";
    }

    /**
     * Members
     */
    @GetMapping("/biz-profiles")
    public String bizProfiles(Model model, @RequestParam(required = false) String filter){

        List<AdminMemberDto> memberDtoList = filter != null && filter.equalsIgnoreCase("counting")
                ? adminService.getMembersWhere(null, "ROLE_BIZ").stream().filter(el -> el.getSurveyCount() > 0).collect(Collectors.toList())
                : adminService.getMembersWhere(null, "ROLE_BIZ");

        model.addAttribute("members", memberDtoList);
        return "admin/biz-profiles";
    }

    /**
     * Responses
     */
    @GetMapping("/responses/surveys/{surveyId}")
    public String surveyResponses(@PathVariable Long surveyId, Model model){

        model.addAttribute("responses", adminService.getMongoResponses(surveyId));
        return "admin/responses";
    }

    @GetMapping("/responses")
    public String getAllSurveyResponses(Model model){

        model.addAttribute("responses", adminService.getMongoResponses(null));
        return "admin/responses";
    }

    @GetMapping("/surveys/{surveyId}/individual/{responseId}")
    public String getResponseDetail(@PathVariable Long surveyId, @PathVariable Long responseId, Model model){
        List<IndividualQuestionMongo> aggregates = mongoService.getIndividualResponseMongo(surveyId, responseId);

        model.addAttribute("survey", mongoService.findSurvey(surveyId));
        model.addAttribute("aggregates", aggregates);

        return "admin/response-detail";
    }

    @ResponseBody
    @PatchMapping("/surveys/{surveyId}/publish")
    public ResponseEntity<ResponseDto<?>> patchSurveyToPublished(@PathVariable Long surveyId){
        surveyService.patchSurveyAboutPublishing(surveyId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, PUBLISH_SURVEY));
    }

    /**
     * user log
     */

    @GetMapping("/surveys/{surveyId}/user-log")
    public String getUserLogFromSurvey(@PathVariable Long surveyId, Model model){
        model.addAttribute("userLogs", adminService.getUserLogsFromSurvey(surveyId));

        return "admin/survey-user-logs";
    }

    /**
     * Feedback
     */
    @GetMapping("/feedbacks")
    public String getFeedbacks(Model model){
        model.addAttribute("feedbacks", adminService.getFeedbacks());

        return "admin/feedbacks";
    }
}
