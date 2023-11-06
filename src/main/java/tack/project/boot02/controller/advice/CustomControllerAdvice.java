package tack.project.boot02.controller.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import tack.project.boot02.service.MemberServiceImpl;


@Log4j2
@RestController
public class CustomControllerAdvice {

    //////////////////////////////////////////////////////////////////////////////////////////////
    @ExceptionHandler(MemberServiceImpl.MemberLoginException.class)
    public ResponseEntity<Map<String, String>> handleException(
    MemberServiceImpl.MemberLoginException e) {

        log.info("--------------------");
        log.info(e.getMessage());

        // 잘못된 Pw를 치면 에러메시지 띄우기.
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("errorMsg", "Login Fail"));
        
    }
    
}
