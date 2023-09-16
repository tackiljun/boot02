package tack.project.boot02.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import tack.project.boot02.dto.MemberCartDTO;
import tack.project.boot02.service.MemberCartService;


@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/cart/")
@RestController
@CrossOrigin
public class MemberCartController {

    ///////////////////////////////////////////////////////////////////////////
    private final MemberCartService cartService;

    ///////////////////////////////////////////////////////////////////////////
    // 담아둔 목록 데이터.
    @PostMapping("add")
    public List<MemberCartDTO> add(@RequestBody MemberCartDTO memberCartDTO) {

        log.info("param: " + memberCartDTO);

        return cartService.addCart(memberCartDTO);

    }

    ///////////////////////////////////////////////////////////////////////////
    @GetMapping("{email}")
    public List<MemberCartDTO> get(@PathVariable("email") String email) {

        return cartService.getCart(email);

    }
    
}
