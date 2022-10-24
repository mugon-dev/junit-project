package com.example.junitproject.web;

import com.example.junitproject.service.BookService;
import com.example.junitproject.web.dto.request.BookSaveReqDto;
import com.example.junitproject.web.dto.response.BookListRespDto;
import com.example.junitproject.web.dto.response.BookResDto;
import com.example.junitproject.web.dto.response.CMResDto;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    // 기본 parsing 전략: key=value&key=value
    // 보낼 데이터 타입 {"key": value, "key": value}
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Validated BookSaveReqDto bookSaveReqDto,
        BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        } else {
        BookResDto bookResDto = bookService.책등록하기(bookSaveReqDto);
            return new ResponseEntity<>(CMResDto.builder()
                .code(1)
                .msg("글 저장 성공")
                .body(bookResDto)
                .build(), HttpStatus.CREATED);
        }
    }

    // 2. 책목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookList = bookService.책목록보기();
        return new ResponseEntity<>(CMResDto.builder()
            .code(1)
            .msg("글 목록보기 성공")
            .body(bookList)
            .build(), HttpStatus.OK);
    }

    // 3. 책한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResDto bookResDto = bookService.책한건보기(id);
        return new ResponseEntity<>(CMResDto.builder()
            .code(1)
            .msg("글 한건보기 성공")
            .body(bookResDto)
            .build(), HttpStatus.OK);
    }

    // 4. 책삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMResDto.builder()
            .code(1)
            .msg("글 삭제 성공")
            .body(null)
            .build(), HttpStatus.OK);
    }

    // 5. 책수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto,BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookResDto bookResDto = bookService.책수정하기(id,bookSaveReqDto);
        return new ResponseEntity<>(CMResDto.builder()
            .code(1)
            .msg("글 수정 성공")
            .body(bookResDto)
            .build(), HttpStatus.OK);

    }

}
