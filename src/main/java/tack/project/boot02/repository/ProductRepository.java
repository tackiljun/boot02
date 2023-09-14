package tack.project.boot02.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tack.project.boot02.entity.Product;
import tack.project.boot02.repository.search.ProductSearch;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    //////////////////////////////////////////////////////////////////////////
    // 상세보기 JPQL.
    @EntityGraph(attributePaths = "images")
    @Query("select p from Product p where p.delFlag = false and p.pno = :pno")
    Product selectOne(@Param("pno") Long pno);
    
}
