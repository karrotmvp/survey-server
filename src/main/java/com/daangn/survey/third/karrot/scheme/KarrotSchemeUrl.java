package com.daangn.survey.third.karrot.scheme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KarrotSchemeUrl {
    private KarrotWidget data;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KarrotWidget {
        private String nodeId;
        private String entryTargetUri;
    }
}

