package tack.project.boot02.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Embeddable  // 자동으로 FK생성.
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

    /////////////////////
    private String fname;

    private int ord;
    
}
