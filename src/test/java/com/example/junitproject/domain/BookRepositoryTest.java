package com.example.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    /**
     * Junit 테스트 특징
     * 1. 테스트 메서드의 순서 보장이 안된다 (순서를 만들어줄려면 Order 어노테이션 사용)
     * 2. 테스트 메서드가 하나 실행 후 종료되면 데이터가 초기화된다. - Transactional 어노테이션
     * 2-1. 그러나 Primary key auto_increment 값이 초기화가 안됨
     * 2-1-1. 해결하기 위해 AfterEach로 sql문을 직접 실행해서 auto_increment를 초기화
     * 2-1-1-1. 단점 : 매번 실행해서 느려짐
     * 2-1-2. SQL 어노테이션에 작성한 sql문을 직접 실행
     * 2-1-2-1. resources/db/tableInit.sql (drop talbe -> create table)
     */

    @Autowired // DI
    private BookRepository bookRepository;

    // @BeforeAll // 테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void 데이터준비(){
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
            .title(title)
            .author(author)
            .build();
        bookRepository.save(book);
    }
    // [데이터 준비+책 목록보기], [데이터 준비 + 책 단건보기]
    // BeforeEach는 그 다음 테스트 완료까지 트랜잭션이 묶임


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
    } // 트랜잭션 종료 (저장된 데이터를 초기화)
    // 2. 책 목록 보기
    @Test
    public void 책목록보기_test(){
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        List<Book> booksPS = bookRepository.findAll();
        // then
        assertEquals(title, booksPS.get(0).getTitle());

    }

    // 3. 책 한건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기_test(){
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
    }

    // 4. 책 수정


    // 5. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test(){
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent()); // false일때 테스트 성공
    }
}