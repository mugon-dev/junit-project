package com.example.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        /**
         * 컨트롤러 title,author -> BookSaveReqDto -> Service에 넘김
         * -> Book 엔티티 변환 -> BookRepository.save(book)
         * -> DB에 저장 (primary key 생성 = id 생성완료)
         * -> save 메서드가 DB에 저장된 Book을 return (DB데이터와 동기화된 데이터)
         */
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
            .title(title)
            .author(author)
            .build();

        // when (테스트 실행)
        // PS = persistance (영구적으로 저장됨 == 영속화된)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
    // 2. 책 목록 보기

    // 3. 책 한건 보기

    // 4. 책 수정

    // 5. 책 삭제
}