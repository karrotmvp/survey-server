package com.daangn.survey.response.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class ChoiceResponsePK implements Serializable {
    private Long choiceId;
    private Long surveyResponseId;
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChoiceResponsePK)) return false;
        ChoiceResponsePK that = (ChoiceResponsePK) o;
        return Objects.equals(choiceId, that.choiceId) && Objects.equals(surveyResponseId, that.surveyResponseId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId, surveyResponseId, questionId);
    }
}
