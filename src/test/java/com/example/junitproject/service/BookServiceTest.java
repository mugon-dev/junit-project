package com.example.junitproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSender;
import com.example.junitproject.web.dto.BookResDto;
import com.example.junitproject.web.dto.BookSaveReqDto;
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
}