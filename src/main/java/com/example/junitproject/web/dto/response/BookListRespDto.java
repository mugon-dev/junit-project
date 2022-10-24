package com.example.junitproject.web.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookListRespDto {
    List<BookResDto> items;

    @Builder
    public BookListRespDto(List<BookResDto> bookList) {
        this.items = bookList;
    }
}
