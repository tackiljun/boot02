package tack.project.boot02.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tack.project.boot02.entity.ProductReview;


public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    
}
