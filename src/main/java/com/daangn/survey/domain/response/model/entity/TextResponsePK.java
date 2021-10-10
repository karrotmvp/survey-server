package com.daangn.survey.domain.response.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
