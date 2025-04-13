package com.example.userinterestcrudrepo.models;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIpFilterRequest {

    private ZonedDateTimeFilter zonedDateTime;
}
