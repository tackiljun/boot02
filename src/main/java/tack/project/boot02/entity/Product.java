package tack.project.boot02.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "images")
public class Product {

    ///////////////////////////////////////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;
    private String pdesc;
    private String writer;
    private int price;
    private boolean delFlag;

    ///////////////////////////////////////////////////////
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    ///////////////////////////////////////////////////////
    // 상품추가 메소드.
    public void addImage(String name) {

        ProductImage pImage = ProductImage.builder()
                                .fname(name)
                                .ord(images.size())
                                .build();

        images.add(pImage);

    }

    ///////////////////////////////////////////////////////
    // 이미지 파일들 다 지우는 메소드.
    public void clearImages() {
        images.clear();
    }

    ///////////////////////////////////////////////////////
    public void changePrice(int price) {
        this.price = price;
    }

    ///////////////////////////////////////////////////////
    public void changePname(String pname) {
        this.pname = pname;
    }

    ///////////////////////////////////////////////////////
    public void changePdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    ///////////////////////////////////////////////////////
    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }
    
}
