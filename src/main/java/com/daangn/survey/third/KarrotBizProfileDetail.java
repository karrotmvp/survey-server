package com.daangn.survey.third;

import com.daangn.survey.core.auth.oauth.UserDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KarrotBizProfileDetail extends UserDetail {
    private KarrotBizProfileData data;

    @Getter
    public class KarrotBizProfileData {
        private KarrotBizProfile bizProfile;

        @Getter
        public class KarrotBizProfile{
            private String id;
            private String name;
            private String imageUrl;
        }
    }


}
