package tack.project.boot02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCartDTO {

    ///////////////////////
    private  Long cno;
    private  String email;
    private Long pno;
    
}
