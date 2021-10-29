package com.daangn.survey.third;

import com.daangn.survey.core.auth.oauth.AbstractUserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KarrotBizProfileDetail extends AbstractUserDetail {
    private KarrotBizProfileData data;

    @Getter
    public static class KarrotBizProfileData {
        private KarrotBizProfile bizProfile;

        @Getter
        public static class KarrotBizProfile{
            private String id;
            private String name;
            private String imageUrl;
            private Region region;
            private String profileUrl;

            @Getter
            public static class Region{
                private String id;
                private String name;
                private String name1Id;
                private String name1;
                private String name2Id;
                private String name2;
                private String name3Id;
                private String name3;
            }
        }
    }
}
