package tack.project.boot02.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tack.project.boot02.entity.Member;


@SpringBootTest
public class MemberTests {

    ////////////////////////////////////////////////////////////////
    @Autowired
    MemberRepository memberRepository;

    ////////////////////////////////////////////////////////////////
    @Test
    public void testInsert() {

        Member member = Member.builder()
            .email("tack89826@hanmail.net")
            .pw("1111")
            .nickname("Zerock")
            .build();

        memberRepository.save(member);

    }

    ////////////////////////////////////////////////////////////////
    @Test
    public void testRead() {

        String email = "user@aaa.com";

        Optional<Member> result = memberRepository.findById(email);

        Member member = result.orElseThrow();

        System.out.println(member);

    }
    
}
