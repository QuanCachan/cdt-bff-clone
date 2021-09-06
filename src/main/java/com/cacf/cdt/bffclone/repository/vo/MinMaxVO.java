package com.cacf.cdt.bffclone.repository.vo;

import lombok.Value;

@Value
public class MinMaxVO<T> {
    T min;
    T max;
}
