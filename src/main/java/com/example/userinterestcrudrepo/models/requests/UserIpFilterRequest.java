package com.example.userinterestcrudrepo.models.requests;

import com.example.userinterestcrudrepo.models.filters.ZonedDateTimeFilter;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIpFilterRequest {

    private String username;
    private String ip;
    private String endpoint;
    private String requestMethod;
    private ZonedDateTimeFilter dateTime;
}
