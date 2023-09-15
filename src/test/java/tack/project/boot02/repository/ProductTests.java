package tack.project.boot02.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import tack.project.boot02.dto.PageRequestDTO;
import tack.project.boot02.dto.PageResponseDTO;
import tack.project.boot02.dto.ProductListDTO;
import tack.project.boot02.entity.Product;


@SpringBootTest
public class ProductTests {

    //////////////////////////////////////////////////////////////////////////////
    @Autowired
    ProductRepository repo;

    //////////////////////////////////////////////////////////////////////////////
    // @Test
    // public void testInsert() {

    //     for(int i = 0; i < 200; i++) {

    //         Product product = Product.builder()
    //         .pname("test"+i)
    //         .pdesc("TEST"+i)
    //         .writer("user00"+i)
    //         .price(4000)
    //         .build();

    //         product.addImage(UUID.randomUUID().toString()+"_aaa.jpg");
    //         product.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
    //         product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

    //         repo.save(product);
    //     } //end for. 
    // }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    @Transactional
    public void testRead1() {

        Optional<Product> result = repo.findById(1L);
        Product product = result.orElseThrow();

        System.out.println(product);
        System.out.println("--------------------");
        System.out.println(product.getImages());

    }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    public void testRead2() {

        Product product = repo.selectOne(1L);

        System.out.println(product);
        System.out.println("--------------------");
        System.out.println(product.getImages());

    }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    public void testDelete() {

        repo.deleteById(1L);

    }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    @Commit
    @Transactional
    public void testUpdate() {

        Optional<Product> result = repo.findById(2L);
        
        Product product = result.orElseThrow();
        
        product.changePrice(6000);

        product.clearImages();

        product.addImage(UUID.randomUUID()+"_newImage.jpg");

        repo.save(product);

    }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    public void testList1() {

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = repo.list(requestDTO);

        for(ProductListDTO dto : result.getDtoList()) {
            System.out.println(dto);
        }

    }

    //////////////////////////////////////////////////////////////////////////////
    @Test
    public void testList2() {

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = repo.listWithReview(requestDTO);

        for(ProductListDTO dto : result.getDtoList()) {
            System.out.println(dto);
        }

    }
    
}
