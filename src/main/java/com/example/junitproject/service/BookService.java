package com.example.junitproject.service;

import com.example.junitproject.domain.Book;
import com.example.junitproject.domain.BookRepository;
import com.example.junitproject.util.MailSender;
import com.example.junitproject.web.dto.request.BookSaveReqDto;
import com.example.junitproject.web.dto.response.BookListRespDto;
import com.example.junitproject.web.dto.response.BookResDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // MailSender 타입중 IoC에 등록된 클래스를 가져옴
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        if(!mailSender.send()){
            throw new RuntimeException("메일이 전송되지 않았습니다.");
        }
        return bookPS.toDto();
    }

    // 2. 책목록보기
    public BookListRespDto 책목록보기() {
        List<BookResDto> dtos = bookRepository.findAll().stream()
            .map(Book::toDto).toList();

        return BookListRespDto.builder()
            .bookList(dtos)
            .build();
    }


    // 3. 책한건보기
    public BookResDto 책한건보기(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            return bookOP.get().toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }


    // 4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id) {
        // 찾지 못했다고 해서 디비에서 오류나지 않음, null을 반환해줌
        // deleteById는 null이면 IllegalArgumentException을 반환
        bookRepository.deleteById(id);
    }

    // 5. 책수정

    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto 책수정하기(Long id, BookSaveReqDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }

    } // 메서드 종료시에 더티체킹(flush)으로 update됨

}
