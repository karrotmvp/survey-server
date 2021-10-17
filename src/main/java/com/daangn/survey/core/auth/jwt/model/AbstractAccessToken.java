package com.daangn.survey.core.auth.jwt.model;

import lombok.Getter;

public abstract class AbstractAccessToken {
    public abstract String getAccessToken();
}
