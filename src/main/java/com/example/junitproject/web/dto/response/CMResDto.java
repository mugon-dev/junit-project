package com.example.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMResDto<T> {
    private Integer code; // 1 성공, -1 실패
    private String msg;
    private T body;

    @Builder
    public CMResDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
