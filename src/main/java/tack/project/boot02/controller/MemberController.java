package tack.project.boot02.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
@Log4j2
public class MemberController {
    
}
