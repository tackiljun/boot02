package tack.project.boot02.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tack.project.boot02.entity.Member;


public interface MemberRepository extends JpaRepository<Member, String> {
    
}
