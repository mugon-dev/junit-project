package com.example.junitproject.web;

import com.example.junitproject.service.BookService;
import com.example.junitproject.web.dto.BookSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    // 기본 parsing 전략: key=value&key=value
    // 보낼 데이터 타입 {"key": value, "key": value}
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto){
        return null;
    }

    // 2. 책목록보기
    public ResponseEntity<?> getBookList(){
        return null;
    }

    // 3. 책한건보기
    public ResponseEntity<?> getBookOne(){
        return null;
    }

    // 4. 책삭제하기
    public ResponseEntity<?> deleteBook(){
        return null;
    }

    // 5. 책수정하기
    public ResponseEntity<?> updateBook(){
        return null;
    }

}
