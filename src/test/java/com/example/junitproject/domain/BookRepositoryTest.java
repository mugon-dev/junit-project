package com.example.junitproject.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책등록_test(){
        System.out.println("책등록 test");
    }
    // 2. 책 목록 보기

    // 3. 책 한건 보기

    // 4. 책 수정

    // 5. 책 삭제
}