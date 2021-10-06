package com.daangn.survey.response.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@Embeddable
public class TextResponsePK implements Serializable {
    private Long surveyResponseId;
    private Long questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextResponsePK)) return false;
        TextResponsePK that = (TextResponsePK) o;
        return Objects.equals(surveyResponseId, that.surveyResponseId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surveyResponseId, questionId);
    }
}
