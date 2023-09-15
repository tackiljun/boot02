package tack.project.boot02.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    ///////////////////////////////////
    @Id
    private String email; // 아이디역할.
    private String pw; // 패스워드.
    private String nickname;
    private boolean admin;
    
}
