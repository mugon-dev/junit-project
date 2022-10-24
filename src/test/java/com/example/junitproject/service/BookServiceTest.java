package com.example.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSender;
import com.example.junitproject.web.dto.BookResDto;
import com.example.junitproject.web.dto.BookSaveReqDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경 생성
class BookServiceTest {

    // @Autowired로 메모리에 올리지 못함
    @InjectMocks // 메모리에 띄울때 Mock 선언해준 객체를 주입해줌
    private BookService bookService;

    @Mock // 가짜 객체 생성
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        // stub
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResDto bookResDto = bookService.책등록하기(dto);

        // then
        assertEquals(dto.getTitle(), bookResDto.getTitle());

        assertThat(dto.getTitle()).isEqualTo(bookResDto.getTitle());
    }

    @Test
    void 책목록보기_테스트() {
        // given

        // stub
        List<Book> books = List.of(
            new Book(1L, "junit강의", "메타코딩"),
            new Book(2L, "spring강의", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);
        // when
        List<BookResDto> dtos = bookService.책목록보기();

        // then
        assertThat(dtos.get(0).getTitle()).isEqualTo("junit강의");
    }
}