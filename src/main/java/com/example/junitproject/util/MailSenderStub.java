package com.example.junitproject.util;

import org.springframework.stereotype.Component;

/**
 * stub = 가정
 */
@Component // IoC 컨테이너 등록
public class MailSenderStub implements MailSender{

    @Override
    public boolean send() {
        return true;
    }
}
