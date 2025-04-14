package com.example.userinterestcrudrepo.models.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ZonedDateTimeFilter {

    private ZonedDateTime from;

    private ZonedDateTime to;
}
