package com.cacf.cdt.bffclone.repository.vo;

import lombok.Value;

import java.time.temporal.Temporal;

@Value
public class DateRangeVO<T extends Temporal> {
    T from;
    T to;
}
