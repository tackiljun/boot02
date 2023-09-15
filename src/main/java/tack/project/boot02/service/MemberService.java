package tack.project.boot02.service;

import jakarta.transaction.Transactional;

import tack.project.boot02.dto.MemberDTO;


@Transactional
public interface MemberService {

    ///////////////////////////////////////////
    MemberDTO login(String email, String pw);

    ///////////////////////////////////////////
    MemberDTO getMemberWithEmail(String email);
    
}
