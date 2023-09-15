package tack.project.boot02.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import tack.project.boot02.dto.MemberDTO;
import tack.project.boot02.service.MemberService;
import tack.project.boot02.service.SocialService;
import tack.project.boot02.util.JWTUtil;


@CrossOrigin
@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    ////////////////////////////////////////////////////////////////////////////////////////////
    private final MemberService memberService;
    private final SocialService socialService;
    private final JWTUtil jwtUtil;

    ////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("kakao")
    public MemberDTO getAuthCode(String code) {

    //public Map<String, String> getAuthCode(String code) {

        log.info("====================");
        log.info(code);

        String email = socialService.getKakaoEmail(code);

        log.info("==========---------------==========");

        MemberDTO memberDTO = memberService.getMemberWithEmail(email);

        //return Map.of("result", "success");
        return memberDTO;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {

        log.info("Parameter: " + memberDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MemberDTO result = memberService.login(
            memberDTO.getEmail(),
            memberDTO.getPw()
        );

        // AccessToken 시간정하기?시간만들기?.
        result.setAccessToken(jwtUtil.generate(Map.of("email", result.getEmail()), 1));

        // RefreshToken 시간정하기?시간만들기?.
        result.setRefreshToken(jwtUtil.generate(Map.of("email", result.getEmail()), 60*24));

        log.info("Return: " + result);

        return  result;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("refresh")
    public Map<String, String> refresh(
        @RequestHeader("Authorization") String accessToken, String refreshToken ) {

        log.info("Refresh.... access: " + accessToken);
        log.info("Refresh... refresh: " + refreshToken);

        //accessToken은 만료되었는지 확인.

        //refreshToken은 만료되지 않았는지 확인.

        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);


        return Map.of(
            "accessToken" , jwtUtil.generate(claims, 1), 
            "refreshToken", jwtUtil.generate(claims, 60*24));

    }
    
}
